package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TransferController {

    private final TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = new JdbcTransferDao();
    }

    // methods here to receive requests from client using transferDao



}
