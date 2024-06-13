package com.eteration.simplebanking.exception;

import com.eteration.simplebanking.model.enums.ErrorStatus;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(ErrorStatus errorStatus) {
        super(String.valueOf(errorStatus));
    }
}