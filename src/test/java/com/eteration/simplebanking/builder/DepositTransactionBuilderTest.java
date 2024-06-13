package com.eteration.simplebanking.builder;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.enums.TransactionStatus;
import com.eteration.simplebanking.model.enums.TransactionType;
import com.eteration.simplebanking.model.request.DepositRequest;
import com.eteration.simplebanking.model.transaction.DepositTransaction;
import com.eteration.simplebanking.model.transaction.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class DepositTransactionBuilderTest {

    @InjectMocks
    private DepositTransactionBuilder depositTransactionBuilder;

    @Test
    void it_should_create_transaction() {
        //given
        Account account = Account.builder().balance(1000.0).build();
        DepositRequest request = DepositRequest.builder()
                .description("Salary deposit")
                .amount(200.0d)
                .build();

        //when
        Transaction result = depositTransactionBuilder.createTransaction(account, request);

        //then
        assertThat(result)
                .isInstanceOf(DepositTransaction.class)
                .extracting(
                        "account",
                        "amount",
                        "transactionType",
                        "transactionStatus",
                        "description"
                )
                .containsExactly(
                        account,
                        200.d,
                        TransactionType.DEPOSIT,
                        TransactionStatus.COMPLETED,
                        "Salary deposit"
                );

        assertThat(account.getBalance()).isEqualTo(1200.0);
    }
}