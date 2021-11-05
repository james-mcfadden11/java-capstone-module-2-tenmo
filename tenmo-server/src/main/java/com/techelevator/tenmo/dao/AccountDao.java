package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    List<Account> getListOfAccounts();
    Account getOneAccount(long accountID);
    Double getBalance(long accountID);
    // get all transfers for one specific user
    List<Transfer> getListOfTransfers(long accountID);
    long getAccountIDFromUserID(long userID);
}
