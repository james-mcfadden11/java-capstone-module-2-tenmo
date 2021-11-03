package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    // eventually need to add validations using @Valid

    private Account fromAccount;
    private Account toAccount;
    private long transferID;
    private BigDecimal amount;

    // transferType: 1 = request, 2 = send
    private int transferType;

    // transferStatus: 1 = pending, 2 = approved, 3 = rejected
    private int transferStatus;

    // for now, including 2 constructors - not sure what we will need
    public Transfer() {
    }

    public Transfer(Account fromAccount, Account toAccount, long transferID, int transferType, int transferStatus, BigDecimal amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transferID = transferID;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.amount = amount;
    }


    // getters and setters


    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public long getTransferID() {
        return transferID;
    }

    public void setTransferID(long transferID) {
        this.transferID = transferID;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


}
