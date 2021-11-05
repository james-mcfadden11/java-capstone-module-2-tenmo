package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
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

    public double viewCurrentBalance(long userID){
        //CRUD methods
        Account account = null;
        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(API_BASE_URL + "accounts/" + userID + "/balance" ,
                            HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();

            if (account == null) {
                return -1.0;
            }

            return account.getBalance();

        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.print(e.getMessage());
        }

        // unsuccessfully accessed account balance
        return -1.0;
    }

//    public History viewTransferHistory(){
//       // return restTemplate.getForObject(API_BASE_URL + "/0808" + API_KEY, History.class);

 //   }


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
