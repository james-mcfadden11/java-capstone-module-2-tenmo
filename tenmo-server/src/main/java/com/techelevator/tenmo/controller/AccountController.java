package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private final AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = new JdbcAccountDao();
    }

    // methods below to receive requests from client using transferDao, rest annotation

}
