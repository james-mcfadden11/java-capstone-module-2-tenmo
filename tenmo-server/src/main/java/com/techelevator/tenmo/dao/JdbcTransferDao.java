package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // below methods to access the database using jdbcTemplate

   @Override
    public Transfer getOneTransfer(long transferID) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfers WHERE transfer_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferID);

        if (result.next()) {
            transfer = mapRowToTransfer(result);
        }

        return transfer;
    }

    // TO-DO: add exception for insufficient funds
    // break this up into private helper methods?
    // all the commented out lines of code are for using BigDecimal class instead of double... for later
    @Override
    public Transfer sendTransfer(Transfer transfer) {
        // first, check if sender has enough $ in account to transfer
        String sql;
        sql = "SELECT balance FROM accounts WHERE account_id = ?";
        Double senderBalance = jdbcTemplate.queryForObject(sql, Double.class, transfer.getFromAccountID());

//        BigDecimal fromAccountBalance = new BigDecimal(jdbcTemplate.queryForObject(sql, Integer.class, transfer.getFromAccountID()));
//        BigDecimal transferAmount = transfer.getAmount();

        if (senderBalance == null || senderBalance < transfer.getAmount()) {
            // insufficient funds in fromAccount to cover transfer
            // throws InsufficientFundsException...?
            return null;
        }

//        if (fromAccountBalance.compareTo(transfer.getAmount()) == -1) {
            // insufficient funds in fromAccount to cover transfer
            // throws InsufficientFundsException...?
//            return null;
//        }

        // update sender balance
        senderBalance -= transfer.getAmount();
        sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        long fromAccountID = transfer.getFromAccountID();
        jdbcTemplate.update(sql, senderBalance, fromAccountID);

//        BigDecimal senderNewBalance = fromAccountBalance.subtract(transfer.getAmount());
//        jdbcTemplate.update(sql, senderNewBalance, transfer.getFromAccountID());

        // update receiver balance
        sql = "SELECT balance from accounts where account_id = ?";
        Double receiverBalance = jdbcTemplate.queryForObject(sql, Double.class, transfer.getToAccountID());
        receiverBalance += transfer.getAmount();
        sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        long toAccountID = transfer.getToAccountID();
        jdbcTemplate.update(sql, receiverBalance, toAccountID);

//        sql = "SELECT balance FROM accounts WHERE account_id = ?";
//        BigDecimal toAccountBalance = new BigDecimal(jdbcTemplate.queryForObject(sql, Integer.class, transfer.getToAccountID()));
//        BigDecimal receiverNewBalance = toAccountBalance.add(transfer.getAmount());

        // add transfer to transfers table (similar to a ledger)
        sql = "INSERT INTO transfers " +
                "(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id";
        Long newTransferId = jdbcTemplate.queryForObject(sql, Long.class, transfer.getTransferType(), transfer.getTransferStatus(),
                transfer.getFromAccountID(), transfer.getToAccountID(), transfer.getAmount());
        if (newTransferId == null) {
            return null;
        }
        return getOneTransfer(newTransferId);
    }


    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();

        transfer.setFromAccountID(rowSet.getLong("account_from"));
        transfer.setFromUsername(getUsernameFromAccountID(transfer.getFromAccountID()));

        transfer.setToAccountID(rowSet.getLong("account_to"));
        transfer.setToUsername(getUsernameFromAccountID(transfer.getToAccountID()));

        transfer.setAmount(rowSet.getDouble("amount"));
        transfer.setTransferID(rowSet.getLong("transfer_id"));
        transfer.setTransferStatus(rowSet.getInt("transfer_status_id"));
        transfer.setTransferType(rowSet.getInt("transfer_type_id"));

        return transfer;
    }

    // created a helper method for this function - probably possible to use only sql to do this job,
    // but this was easier to read
    private String getUsernameFromAccountID(long accountID) {
        String sql = "select username from users join accounts using(user_id) where account_id = ?";
        String username = jdbcTemplate.queryForObject(sql, String.class, accountID);
        return username;
    }
}
