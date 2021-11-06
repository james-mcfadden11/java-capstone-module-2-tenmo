package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private final String API_BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken;

    public AccountService(String authToken) {
        this.authToken = authToken;
    }

    public double viewCurrentBalance(long userID){
        //CRUD methods
        Account account = null;
        try {
            ResponseEntity<Account> response = restTemplate.exchange(API_BASE_URL + "accounts/" + userID + "/balance",
                    HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
            if (account == null) {
                return -1.0;
            }
            return account.getBalance();
        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.print(e.getMessage());
        }
        // unsuccessfully accessed account balance returns -1
        return -1.0;
    }

    public Transfer[] viewTransferHistory(long userID) {
        Transfer[] transferHistory = null;
        long accountID = -1;

        // need to change userID --> accountID --- CHANGED
        // this is probably a hack way of doing this...
        Account account = null;
        try {
            ResponseEntity<Account> accountResponse = restTemplate.exchange(API_BASE_URL + "accounts/" + accountID, HttpMethod.GET,
                    makeAuthEntity(), Account.class);
            accountID = accountResponse.getBody().getAccountID();
        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.print(e.getMessage());
        }

        try {
            ResponseEntity<Transfer[]> transferResponse = restTemplate.exchange(API_BASE_URL + "accounts/" + accountID + "/transfers",
                    HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transferHistory = transferResponse.getBody();
            return transferHistory;
        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.print(e.getMessage());
        }
        return transferHistory;
    }

    private HttpEntity<Account> makeAccountEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(account, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
