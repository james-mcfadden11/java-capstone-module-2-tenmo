package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountService {
    private final String API_BASE_URL;
    private final String API_KEY;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountService(String api_base_url, String api_key, RestTemplate restTemplate) {
        API_BASE_URL = "http://localhost:8080/";
        API_KEY = api_key;
        this.restTemplate = restTemplate;
    }

    public double  viewCurrentBalance(long accountID){
      //return restTemplate.getForObject(API_BASE_URL + "/0808" + API_KEY, Balance.class);
        //CRUD methods
        Account account = null;
        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(API_BASE_URL + "accounts/" + accountID + "/balance" ,
                            HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
            return account.getBalance();
        } catch (RestClientResponseException | ResourceAccessException e) {
            System.out.print(e.getMessage());
        }

    }

//    public History viewTransferHistory(){
//       // return restTemplate.getForObject(API_BASE_URL + "/0808" + API_KEY, History.class);

 //   }



    private HttpEntity<Account> makeReservationEntity(Reservation reservation) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(reservation, headers);
    }
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
