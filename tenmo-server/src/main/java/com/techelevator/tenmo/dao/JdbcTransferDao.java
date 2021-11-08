package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.Exception.InsufficientFundsException;
import com.techelevator.tenmo.Exception.TransferNotFoundException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

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
    public Transfer getOneTransfer(long transferID) throws TransferNotFoundException {
       Transfer transfer = null;
       String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
               "FROM transfers WHERE transfer_id = ?";
       SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferID);

       if (result.next()) {
           transfer = mapRowToTransfer(result);
       } else {
           throw new TransferNotFoundException();
       }
       return transfer;
   }

    @Override
    public Transfer sendTransfer(Transfer transfer) throws InsufficientFundsException, TransferNotFoundException {
        String sql;

        // find accounts for which transfer is being sent/received
        Account sender = null;
        sql = "SELECT account_id, user_id, balance FROM accounts WHERE user_id = ?";
        SqlRowSet senderRowSet = jdbcTemplate.queryForRowSet(sql, transfer.getFromUserID());
        if (senderRowSet.next()) {
            sender = mapRowToAccount(senderRowSet);
        }

        Account receiver = null;
        SqlRowSet receiverRowSet = jdbcTemplate.queryForRowSet(sql, transfer.getToUserID());
        if (receiverRowSet.next()) {
            receiver = mapRowToAccount(receiverRowSet);
        }

        // check if sender has enough $ in account to transfer
        if (sender.getBalance() < transfer.getAmount()) {
            System.out.println("Insufficient funds!");
            throw new InsufficientFundsException();
        }

        // update sender and receiver balances
        sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        sender.setBalance(sender.getBalance() - transfer.getAmount());
        jdbcTemplate.update(sql, sender.getBalance(), sender.getAccountID());
        receiver.setBalance(receiver.getBalance() + transfer.getAmount());
        jdbcTemplate.update(sql, receiver.getBalance(), receiver.getAccountID());

        // add transfer to transfers table (similar to a ledger)
        sql = "INSERT INTO transfers " +
                "(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id";
        Long newTransferId = jdbcTemplate.queryForObject(sql, Long.class, transfer.getTransferType(), transfer.getTransferStatus(),
                sender.getAccountID(), receiver.getAccountID(), transfer.getAmount());
        if (newTransferId == null) {
            return null;
        }
        return getOneTransfer(newTransferId);
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountID(rs.getLong("account_id"));
        account.setBalance(rs.getDouble("balance"));
//        account.setUsername(rs.getString("username"));
        account.setUserID(rs.getLong("user_id"));
        return account;
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
