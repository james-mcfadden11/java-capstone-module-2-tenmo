package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TransferDao {

    // get all transfers for one specific user
    List<Transfer> getListOfTransfers();

    Transfer getOneTransfer(long transferID);

    // returns the transfer that was created for verification
    Transfer sendTransfer(Transfer transfer);

}
