package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.Exception.AccountNotFoundException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    List<Account> getListOfAccounts();
    Account getOneAccount(long accountID) throws AccountNotFoundException;
    Double getBalance(long accountID) throws AccountNotFoundException;
    // get all transfers for one specific user
    List<Transfer> getListOfTransfers(long accountID) throws AccountNotFoundException;
    long getAccountIDFromUserID(long userID);
}
