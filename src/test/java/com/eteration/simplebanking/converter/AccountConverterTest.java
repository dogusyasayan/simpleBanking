package com.eteration.simplebanking.converter;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.enums.TransactionType;
import com.eteration.simplebanking.model.response.AccountResponse;
import com.eteration.simplebanking.model.response.AccountTransactionResponse;
import com.eteration.simplebanking.model.transaction.DepositTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class AccountConverterTest {

    @InjectMocks
    private AccountConverter accountConverter;

    @Test
    void apply_should_convert_account_to_account_response() {
        // given
        Account account = new Account();
        account.setAccountNumber("ACC123");
        account.setBalance(1000.0);
        account.setOwner("customer");
        account.setCreatedDate(LocalDateTime.now());

        // when
        AccountResponse accountResponse = accountConverter.apply(account);

        // then
        assertThat(accountResponse).isNotNull();
        assertThat(accountResponse.getAccountNumber()).isEqualTo("ACC123");
        assertThat(accountResponse.getBalance()).isEqualTo(1000.0);
        assertThat(accountResponse.getOwner()).isEqualTo("customer");
        assertThat(accountResponse.getCreatedDate()).isEqualTo(account.getCreatedDate());
        assertThat(accountResponse.getTransactionResponses()).isEmpty();
    }

    @Test
    void apply_should_convert_account_and_transaction_to_account_transaction_response() {
        // given
        Account account = new Account();
        account.setBalance(1000.0);

        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(200.0);
        transaction.setTransactionType(TransactionType.DEPOSIT);

        // when
        AccountTransactionResponse transactionResponse = accountConverter.apply(account, transaction);

        // then
        assertThat(transactionResponse).isNotNull();
        assertThat(transactionResponse.getPreviousBalance()).isEqualTo("Previous Balance: 1000.0");
        assertThat(transactionResponse.getTransactionAmount()).isEqualTo("Transaction Amount: 200.0");
        assertThat(transactionResponse.getCurrentBalance()).isEqualTo("Current Balance: 1200.0");
    }
}