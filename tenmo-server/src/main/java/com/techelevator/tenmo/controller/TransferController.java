package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TransferController {

    private final TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = new JdbcTransferDao();
    }

    // methods below to receive requests from client using transferDao, REST annotation

    @RequestMapping(path = "/transfers/{transferID}", method = RequestMethod.GET)
    public Transfer getOneTransfer(@PathVariable long transferID) {
        return transferDao.getOneTransfer(transferID);
    }

    // TO-DO: restrict access to sender only
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public Transfer sendTransfer(@Valid @RequestBody Transfer transfer) {
        return transferDao.sendTransfer(transfer);
    }

}
