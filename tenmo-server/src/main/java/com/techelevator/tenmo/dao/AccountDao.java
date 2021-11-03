package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    List<User> getListOfUsers();
    User getOneUser(long accountID);
    BigDecimal getBalance(long accountID);
}
