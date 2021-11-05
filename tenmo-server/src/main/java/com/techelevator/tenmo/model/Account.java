package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    // eventually need to add validations using @Valid

    private BigDecimal balance;
    private long accountID;
    private String username;

    // foreign key to userID - one user can have more than one account
    // User class and corresponding DAO primarily deal with username/password/authentication
    private long userID;

    // for now, including 2 constructors - not sure what we will need
    public Account() {
    }

    public Account(long accountID, long userID, BigDecimal balance, String username) {
        this.accountID = accountID;
        this.userID = userID;
        this.balance = balance;
        this.username = username;
    }


    // getters and setters for all fields

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
