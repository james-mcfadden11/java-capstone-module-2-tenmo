package com.techelevator.tenmo.services;

import org.springframework.web.client.RestTemplate;

public class AccountService {
    private final String API_BASE_URL;
    private final String API_KEY;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountService(String api_base_url, String api_key, RestTemplate restTemplate) {
        API_BASE_URL = api_base_url;
        API_KEY = api_key;
        this.restTemplate = restTemplate;
    }

    public Balance viewCurrentBalance(){
      return restTemplate.getForObject(API_BASE_URL + "/0808" + API_KEY, Balance.class);

    }

    public History viewTransferHistory(){
        return restTemplate.getForObject(API_BASE_URL + "/0808" + API_KEY, History.class);
    }

    public
}
