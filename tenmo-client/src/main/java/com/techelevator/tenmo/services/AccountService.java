package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.apiguardian.api.API;
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

//    public History viewTransferHistory(){
//       // return restTemplate.getForObject(API_BASE_URL + "/0808" + API_KEY, History.class);

 //   }

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

    // to be moved over to TransferService later
    public Transfer viewTransferDetails(long transferID) {

        if (transferID == 0) {
            return null;
        }

        Transfer transfer = null;

        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "transfers/" + transferID,
                    HttpMethod.GET, makeAuthEntity(), Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.print(e.getMessage());
        }

        return transfer;
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

    public Transfer sendTransfer(Transfer transfer) {
        Transfer createdTransfer = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "/transfers",
                    HttpMethod.POST, makeTransferEntity(transfer), Transfer.class);
            createdTransfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.print(e.getMessage());
        }
        return createdTransfer;
    }

    // to be used for POST request
    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }

    // to be used for GET request
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
