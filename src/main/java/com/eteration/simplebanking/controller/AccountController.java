package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.request.BillPaymentRequest;
import com.eteration.simplebanking.model.request.CreateAccountRequest;
import com.eteration.simplebanking.model.request.DepositRequest;
import com.eteration.simplebanking.model.request.WithdrawalRequest;
import com.eteration.simplebanking.model.response.AccountResponse;
import com.eteration.simplebanking.model.response.AccountTransactionResponse;
import com.eteration.simplebanking.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(createAccountRequest);
    }

    @DeleteMapping("/v1/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAccount(@PathVariable Long accountId) {
        return accountService.deleteAccount(accountId);
    }

    @GetMapping("/v1/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse getAccount(@PathVariable Long accountId) {
        return accountService.getAccount(accountId);
    }

    //yatırma
    @PostMapping("/v1/credit/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountTransactionResponse credit(@RequestBody DepositRequest depositRequest, @PathVariable Long accountId) {
        return accountService.credit(depositRequest, accountId);
    }

    //çekme
    @PostMapping("/v1/debit/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountTransactionResponse debit(@RequestBody WithdrawalRequest withdrawalRequest, @PathVariable Long accountId) {
        return accountService.debit(withdrawalRequest, accountId);
    }

    @PostMapping("/v1/payment/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountTransactionResponse billPayment(@RequestBody BillPaymentRequest billPaymentRequest, @PathVariable Long accountId) {
        return accountService.billPayment(billPaymentRequest, accountId);
    }
}