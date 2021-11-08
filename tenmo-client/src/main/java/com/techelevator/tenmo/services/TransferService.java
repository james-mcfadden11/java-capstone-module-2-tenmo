package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private final String API_BASE_URL;
    private final String API_KEY;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferService(String api_base_url, String api_key, RestTemplate restTemplate) {
        API_BASE_URL = api_base_url;
        API_KEY = api_key;
        this.restTemplate = restTemplate;
    }

    public double sendBucks(long amount){
        Account account = null;
        Transfer transfer = null;
        try {
            ResponseEntity<Transfer> TransferResponse = restTemplate.exchange(API_BASE_URL +
                    "accounts/" + AccountID + "transfer/" + amount, HttpMethod.GET, makeAuthEntity(), Transfer.class);
            account = response.getBody();
        } catch (RestClientException | ResourceAccessException e) {
            system.out.print(e.getMessage());
        }
        return transfer;
//        Transfer createdTransfer = null;
//        try {
//            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "/transfers",
//                    HttpMethod.POST, makeTransferEntity(transfer), Transfer.class);
//            createdTransfer = response.getBody();
//        } catch (RestClientResponseException | ResourceAccessException e) {
//            System.out.print(e.getMessage());
//        }
//        return createdTransfer;





    }

    private HttpEntity<Transfer> transferHttpEntity (Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
