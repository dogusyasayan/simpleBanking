package com.eteration.simplebanking.builder;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.request.CreateAccountRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class AccountBuilderTest {

    @InjectMocks
    private AccountBuilder accountBuilder;

    @Test
    void it_should_build() {
        //given
        CreateAccountRequest request = CreateAccountRequest.builder()
                .owner("customer")
                .build();

        //when
        Account result = accountBuilder.buildAccount(request);

        //then
        assertThat(result.getOwner()).isEqualTo("customer");
    }
}