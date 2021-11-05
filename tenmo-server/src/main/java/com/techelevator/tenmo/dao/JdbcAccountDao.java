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
        String sql = "SELECT account_id, user_id, balance, username FROM accounts JOIN users USING(user_id)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while (results.next()) {
            Account account = mapRowToAccount(results);
            accounts.add(account);
        }

        return accounts;
    }

    @Override
    public Account getOneAccount(long accountID) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance, username FROM accounts JOIN users USING(user_id) WHERE account_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountID);

        if (result.next()) {
            account = mapRowToAccount(result);
        }

        return account;
    }

    @Override
    public BigDecimal getBalance(long accountID) {
        BigDecimal balance = null;
        String sql = "SELECT balance FROM accounts WHERE account_id = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, accountID);
    }

    // gets all transfers to or from an account
    @Override
    public List<Transfer> getListOfTransfers(long accountID) {
        List<Transfer> listOfTransfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfers WHERE account_from = ? OR account_to = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountID, accountID);

        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            listOfTransfers.add(transfer);
        }

        return listOfTransfers;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountID(rs.getLong("account_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setUsername(rs.getString("username"));
        account.setUserID(rs.getLong("user_id"));
        return account;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();

        transfer.setFromAccountID(rowSet.getLong("account_from"));
        transfer.setToAccountID(rowSet.getLong("account_to"));
        transfer.setAmount(rowSet.getDouble("amount"));
        transfer.setTransferID(rowSet.getLong("transfer_id"));
        transfer.setTransferStatus(rowSet.getInt("transfer_status_id"));
        transfer.setTransferType(rowSet.getInt("transfer_type_id"));

        return transfer;
    }

}
