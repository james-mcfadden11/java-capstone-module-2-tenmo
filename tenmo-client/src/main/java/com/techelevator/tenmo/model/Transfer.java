package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    // eventually need to add validations using @Valid

    private long fromAccountID;
    private String fromUsername;

    private long toAccountID;
    private String toUsername;

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

    @Override
    public String toString() {
        return "   " + this.transferID + "        " + this.fromUsername + "    " + this.toUsername + "    " + this.amount;
    }

    public String detailedToString(String fromOrTo) {
        // transferType: 1 = request, 2 = send
        // transferStatus: 1 = pending, 2 = approved, 3 = rejected
        String transferType;
        String transferStatus;

        if (this.transferType == 1) {
            transferType = "Request";
        } else if (this.transferType == 2) {
            transferType = "Send";
        } else {
            transferType = "?";
        }

        if (this.transferStatus == 1) {
            transferStatus = "Pending";
        } else if (this.transferStatus == 2) {
            transferStatus = "Approved";
        } else if (this.transferStatus == 3){
            transferStatus = "Rejected";
        } else {
            transferStatus = "?";
        }

        if (fromOrTo.equals("from")) {
            return "ID: " + this.transferID + "\n" +
                    "From: " + this.fromUsername + " (me) \n" +
                    "To: " + this.toUsername + "\n" +
                    "Type: " + transferType + "\n" +
                    "Status: " + transferStatus + "\n" +
                    "Amount: " + this.amount;
        } else if (fromOrTo.equals("to")) {
            return "ID: " + this.transferID + "\n" +
                    "From: " + this.fromUsername + "\n" +
                    "To: " + this.toUsername + " (me) \n" +
                    "Type: " + transferType + "\n" +
                    "Status: " + transferStatus + "\n" +
                    "Amount: " + this.amount;
        } else {
            return "Something went wrong...";
        }
    }

}

