package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.Exception.InsufficientFundsException;
import com.techelevator.tenmo.Exception.TransferNotFoundException;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.stereotype.Component;

import java.util.List;

public interface TransferDao {

    // need a method to return all transfers in ledger (similar to actual venmo) ??

    Transfer getOneTransfer(long transferID) throws TransferNotFoundException;

    // returns the transfer that was created for verification
    Transfer sendTransfer(Transfer transfer) throws InsufficientFundsException, TransferNotFoundException;

}
