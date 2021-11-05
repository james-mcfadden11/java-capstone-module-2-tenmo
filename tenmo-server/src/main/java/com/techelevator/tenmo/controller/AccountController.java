package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountController {

    private final AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = new JdbcAccountDao();
    }

    // below are methods to receive requests from client using transferDao, REST annotation
    // and return appropriate data

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    public List<Account> getListOfAccounts() {
        return accountDao.getListOfAccounts();
    }

    // TO-DO: this method needs to throw a new exception - AccountNotFoundException
    @RequestMapping(path = "/accounts/{accountID}", method = RequestMethod.GET)
    public Account getOneAccount(@PathVariable long accountID) {
        return accountDao.getOneAccount(accountID);
    }

    // TO-DO: need to restrict access to see account balance to authorized user only
    @RequestMapping(path = "/accounts/{accountID}/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable long accountID) {
        return accountDao.getBalance(accountID);
    }

    @RequestMapping(path = "/accounts/{accountID}/transfers", method = RequestMethod.GET)
    public List<Transfer> getListOfTransfers(@PathVariable long accountID) {
        return accountDao.getListOfTransfers(accountID);
    }

}
