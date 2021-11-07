package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.Exception.InsufficientFundsException;
import com.techelevator.tenmo.Exception.TransferNotFoundException;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

    private final TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = new JdbcTransferDao();
    }

    // methods below to receive requests from client using transferDao, REST annotation

    @RequestMapping(path = "/transfers/{transferID}", method = RequestMethod.GET)
    public Transfer getOneTransfer(@PathVariable long transferID) throws TransferNotFoundException {
        return transferDao.getOneTransfer(transferID);
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public Transfer sendTransfer(@Valid @RequestBody Transfer transfer) throws TransferNotFoundException, InsufficientFundsException {
        return transferDao.sendTransfer(transfer);
    }

}
