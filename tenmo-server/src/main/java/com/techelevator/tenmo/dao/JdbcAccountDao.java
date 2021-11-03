package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public class JdbcAccountDao implements AccountDao {

    // need to fill out the below methods

    @Override
    public List<User> getListOfUsers() {
        return null;
    }

    @Override
    public User getOneUser(long accountID) {
        return null;
    }

    @Override
    public BigDecimal getBalance(long accountID) {
        return null;
    }

}
