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
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // below are methods to access the database

    @Override
    public List<Account> getListOfAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT user_id, username, account_id, balance FROM accounts JOIN users USING(user_id)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while (results.next()) {
            Account account = mapRowToAccount(results);
            accounts.add(account);
        }

        return accounts;
    }

    @Override
    public Account getOneAccount(long userID) {
        Account account = null;
        String sql = "SELECT account_id, username, balance, user_id " +
                "FROM users JOIN accounts USING(user_id) WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userID);

        if (result.next()) {
            account = mapRowToAccount(result);
        }

        return account;
    }

    @Override
    public Double getBalance(long userID) {
        Double balance = null;
        String sql = "SELECT balance FROM accounts WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, userID);
    }

    // get all transfers to or from an account
    @Override
    public List<Transfer> getListOfTransfers(long userID) {
        List<Transfer> listOfTransfers = new ArrayList<>();
        Account account = null;

        // get account info for user
        String sql = "SELECT account_id, balance, username, user_id " +
                "FROM accounts JOIN users USING(user_id) WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userID);
        if (result.next()) {
            account = mapRowToAccount(result);
        }

        // get transfers to or from the logged in user
        sql = "SELECT transfer_id, amount, account_from, account_to, transfer_status_id, transfer_type_id" +
                " FROM transfers";
        SqlRowSet transfers = jdbcTemplate.queryForRowSet(sql);
        while (transfers.next()) {
            Transfer transfer = mapRowToTransfer(transfers);

            if (transfer.getFromAccountID() == account.getAccountID()) {
                listOfTransfers.add(transfer);
            }
            // in the odd case that a user would transfer to themself, need to have 2 if statements
            // to show transfer out and transfer in
            if (transfer.getToAccountID() == account.getAccountID()) {
                listOfTransfers.add(transfer);
            }
        }

        return listOfTransfers;


//        // transfers from the user
//        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
//                "FROM users JOIN accounts USING(user_id) JOIN transfers ON account_id = account_from" +
//                " WHERE user_id = ?";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userID);
//
//        while (results.next()) {
//            Transfer transfer = mapRowToTransfer(results);
//            listOfRelevantTransfers.add(transfer);
//        }
//
//        // transfers to the user
//        sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
//                "FROM users JOIN accounts USING(user_id) JOIN transfers ON account_id = account_to" +
//                " WHERE user_id = ?";
//        results = jdbcTemplate.queryForRowSet(sql, userID);
//
//        while (results.next()) {
//            Transfer transfer = mapRowToTransfer(results);
//            listOfRelevantTransfers.add(transfer);
//        }

    }

    public long getAccountIDFromUserID(long userID) {
        String sql = "";
        Long accountID = jdbcTemplate.queryForObject(sql, Long.class, userID);
        if (accountID == null) {
            return -1;
        }
        return accountID;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountID(rs.getLong("account_id"));
        account.setBalance(rs.getDouble("balance"));
        account.setUsername(rs.getString("username"));
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
