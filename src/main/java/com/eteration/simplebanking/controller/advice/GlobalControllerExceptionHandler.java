package com.eteration.simplebanking.controller.advice;

import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.response.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(AccountNotFoundException exception) {
        return instanceError(exception, "account-not-found", HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handle(InsufficientBalanceException exception) {
        return instanceError(exception, "insufficient-balance", HttpStatus.NOT_FOUND);
    }


    private ResponseEntity<ErrorResponse> instanceError(RuntimeException ex, String errorMessage, HttpStatus status) {
        log.error(errorMessage + " exception occurred.", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .exception(ex.getClass().getSimpleName())
                .errors(Collections.singletonList(ex.getMessage()))
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }
}

