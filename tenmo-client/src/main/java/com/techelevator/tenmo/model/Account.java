package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    // eventually need to add validations using @Valid

    private double balance;
    private long accountID;
    private String username;
    private long userID;

    // for now, including 2 constructors - not sure what we will need
    public Account() {
    }

    public Account(long accountID, long userID, double balance, String username) {
        this.accountID = accountID;
        this.userID = userID;
        this.balance = balance;
        this.username = username;
    }

    // for getBalance() method
    public Account(double balance) {
        this.balance = balance;
    }

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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return this.userID + "                " + this.username;
    }

}