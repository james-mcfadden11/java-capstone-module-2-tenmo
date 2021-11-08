package com.techelevator.tenmo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Account not found.")
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
        super("Insufficient funds! Please try again.");
    }
}
