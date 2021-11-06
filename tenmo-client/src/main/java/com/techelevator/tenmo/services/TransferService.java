package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.ResponseEntity;
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
        Transfer transfer = null;
        ResponseEntity<Transfer> transferResponse = restTemplate.exchange(API_BASE_URL + "accounts/" + accountID)




    }
}
