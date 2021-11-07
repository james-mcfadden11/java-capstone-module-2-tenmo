package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class Transfer {

    // eventually need to add validations using @Valid

    private long fromAccountID;
    private String fromUsername;

    private long toAccountID;
    private String toUsername;

    private long toUserID;
    private long fromUserID;

    private long transferID;
    private double amount;

    // transferType: 1 = request, 2 = send
    private int transferType;

    // transferStatus: 1 = pending, 2 = approved, 3 = rejected
    private int transferStatus;

    // for now, including 2 constructors - not sure what we will need
    public Transfer() {
    }

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

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public long getToUserID() {
        return toUserID;
    }

    public void setToUserID(long toUserID) {
        this.toUserID = toUserID;
    }

    public long getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(long fromUserID) {
        this.fromUserID = fromUserID;
    }
}
