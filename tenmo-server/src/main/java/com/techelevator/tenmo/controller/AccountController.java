package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.Exception.AccountNotFoundException;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {

    private final AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = new JdbcAccountDao();
    }

    // below are methods to receive requests from client using transferDao, REST annotation
    // and return appropriate data

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    public List<Account> getListOfAccounts() {
        return accountDao.getListOfAccounts();
    }

    @RequestMapping(path = "/accounts/{userID}", method = RequestMethod.GET)
    public Account getOneAccount(@PathVariable long userID) throws AccountNotFoundException {
        return accountDao.getOneAccount(userID);
    }

    @RequestMapping(path = "/accounts/{userID}/balance", method = RequestMethod.GET)
    public Double getBalance(@PathVariable long userID) throws AccountNotFoundException {
        return accountDao.getBalance(userID);
    }

    @RequestMapping(path = "/accounts/{userID}/transfers", method = RequestMethod.GET)
    public List<Transfer> getListOfTransfers(@PathVariable long userID) throws AccountNotFoundException {
        return accountDao.getListOfTransfers(userID);
    }

//    @RequestMapping(path = "/accounts/getAccountID/{userID}", method = RequestMethod.GET)
//    public long getAccountIDFromUserID(@PathVariable long userID) {
//        return accountDao.getAccountIDFromUserID(userID);
//    }

}
