package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private final String API_BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken;

    public TransferService(String authToken) {
        this.authToken = authToken;
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
        System.out.println("Transfer successfully sent!");
        return createdTransfer;
    }

    public Transfer viewTransferDetails(long transferID) {
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
