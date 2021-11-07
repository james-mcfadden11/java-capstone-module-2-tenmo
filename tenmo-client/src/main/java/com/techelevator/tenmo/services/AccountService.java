package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountService {
    private final String API_BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken;

    public AccountService(String authToken) {
        this.authToken = authToken;
    }

    public Account[] getListOfAccounts() {
        Account[] listOfAccounts = null;
        try {
            ResponseEntity<Account[]> response = restTemplate.exchange(API_BASE_URL + "/accounts",
                    HttpMethod.GET, makeAuthEntity(), Account[].class);
            listOfAccounts = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.print(e.getMessage());
        }
        return listOfAccounts;
    }

    public double viewCurrentBalance(long userID){
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
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "accounts/" + userID + "/transfers",
                    HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transferHistory = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.print(e.getMessage());
        }
        return transferHistory;
    }

    // to be used for GET request
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
