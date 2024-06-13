package com.eteration.simplebanking.exception;


import com.eteration.simplebanking.model.enums.ErrorStatus;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(ErrorStatus errorStatus) {
        super(String.valueOf(errorStatus));
    }
}