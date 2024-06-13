package com.eteration.simplebanking.builder;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.request.CreateAccountRequest;
import com.eteration.simplebanking.utils.NumberGenerator;
import org.springframework.stereotype.Component;

@Component
public class AccountBuilder {

    public Account buildAccount(CreateAccountRequest createAccountRequest) {
        return Account.builder()
                .owner(createAccountRequest.getOwner())
                .accountNumber(NumberGenerator.generateAccountNumber())
                .build();
    }
}
