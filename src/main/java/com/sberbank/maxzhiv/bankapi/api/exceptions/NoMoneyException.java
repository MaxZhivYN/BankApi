package com.sberbank.maxzhiv.bankapi.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class NoMoneyException extends RuntimeException {
    public NoMoneyException(String message) {
        super(message);
    }
}
