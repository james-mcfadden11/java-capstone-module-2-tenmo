package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // need to fill out the below methods to access database using jdbcTemplate
    // testing git 

    @Override
    public List<Transfer> getListOfTransfers() {
        return null;
    }

    @Override
    public Transfer getOneTransfer(long transferID) {
        return null;
    }

    @Override
    public Transfer sendTransfer(Transfer transfer) {
        return null;
    }
}
