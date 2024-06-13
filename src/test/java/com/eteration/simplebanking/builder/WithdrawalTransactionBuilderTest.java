package com.eteration.simplebanking.builder;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.enums.TransactionStatus;
import com.eteration.simplebanking.model.enums.TransactionType;
import com.eteration.simplebanking.model.request.WithdrawalRequest;
import com.eteration.simplebanking.model.transaction.Transaction;
import com.eteration.simplebanking.model.transaction.WithdrawalTransaction;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class WithdrawalTransactionBuilderTest {

    @InjectMocks
    private WithdrawalTransactionBuilder withdrawalTransactionBuilder;

    @Test
    void it_should_create_transaction() {
        //given
        Account account = Account.builder().balance(1000.0).build();
        WithdrawalRequest request = WithdrawalRequest.builder()
                .description("Cash withdrawal")
                .amount(200.0d)
                .build();

        //when
        Transaction result = withdrawalTransactionBuilder.createTransaction(account, request);

        //then
        assertThat(result)
                .isNotNull()
                .isInstanceOf(WithdrawalTransaction.class)
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
                        TransactionType.WITHDRAWAL,
                        TransactionStatus.COMPLETED,
                        "Cash withdrawal"
                );

        assertThat(account.getBalance()).isEqualTo(800.0);
    }

    @Test
    void it_should_throw_insufficient_balance_exception() {
        //given
        Account account = Account.builder().balance(100.0).build();
        WithdrawalRequest request = WithdrawalRequest.builder()
                .description("Cash withdrawal")
                .amount(200.0d)
                .build();

        //when
        InsufficientBalanceException thrown = (InsufficientBalanceException) catchThrowable(() -> withdrawalTransactionBuilder.createTransaction(account, request));

        //then
        AssertionsForClassTypes.assertThat(thrown).isInstanceOf(InsufficientBalanceException.class);
    }
}