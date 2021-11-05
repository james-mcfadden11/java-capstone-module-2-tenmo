package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    // eventually need to add validations using @Valid

    private long fromAccountID;
    private long toAccountID;
    private long transferID;
    private double amount;

    // transferType: 1 = request, 2 = send
    private int transferType;

    // transferStatus: 1 = pending, 2 = approved, 3 = rejected
    private int transferStatus;

    // for now, including 2 constructors - not sure what we will need
    public Transfer() {
    }

    public Transfer(long fromAccountID, long toAccountID, long transferID, int transferType, int transferStatus, double amount) {
        this.fromAccountID = fromAccountID;
        this.toAccountID = toAccountID;
        this.transferID = transferID;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.amount = amount;
    }


    // getters and setters

    public long getFromAccountID() {
        return fromAccountID;
    }

    public void setFromAccountID(long fromAccountID) {
        this.fromAccountID = fromAccountID;
    }

    public long getToAccountID() {
        return toAccountID;
    }

    public void setToAccountID(long toAccountID) {
        this.toAccountID = toAccountID;
    }

    public long getTransferID() {
        return transferID;
    }

    public void setTransferID(long transferID) {
        this.transferID = transferID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public int getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

}

