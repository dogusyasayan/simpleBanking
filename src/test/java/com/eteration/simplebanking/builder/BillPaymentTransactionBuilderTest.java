package com.eteration.simplebanking.builder;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.enums.TransactionStatus;
import com.eteration.simplebanking.model.enums.TransactionType;
import com.eteration.simplebanking.model.request.BillPaymentRequest;
import com.eteration.simplebanking.model.transaction.BillPaymentTransaction;
import com.eteration.simplebanking.model.transaction.Transaction;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class BillPaymentTransactionBuilderTest {

    @InjectMocks
    private BillPaymentTransactionBuilder billPaymentTransactionBuilder;

    @Test
    void it_should_create_transaction() {
        //given
        Account account = Account.builder().balance(1000.0).build();
        BillPaymentRequest request = BillPaymentRequest.builder()
                .amount(200.0)
                .billNumber("123456")
                .billerName("Electric Company")
                .build();

        //when
        Transaction result = billPaymentTransactionBuilder.createTransaction(account, request);

        //then
        assertThat(result)
                .isInstanceOf(BillPaymentTransaction.class)
                .extracting(
                        "account",
                        "amount",
                        "transactionType",
                        "transactionStatus",
                        "billNumber",
                        "billerName"
                )
                .containsExactly(
                        account,
                        200.0,
                        TransactionType.PAYMENT,
                        TransactionStatus.COMPLETED,
                        "123456",
                        "Electric Company"
                );
        assertThat(account.getBalance()).isEqualTo(800.0);
    }

    @Test
    void it_should_throw_insufficient_balance_exception() {
        //given
        Account account = Account.builder().balance(100.0).build();
        BillPaymentRequest request = BillPaymentRequest.builder()
                .amount(200.0)
                .billNumber("123456")
                .billerName("Electric Company")
                .build();

        //when
        InsufficientBalanceException thrown = (InsufficientBalanceException) catchThrowable(() -> billPaymentTransactionBuilder.createTransaction(account, request));

        //then
        AssertionsForClassTypes.assertThat(thrown).isInstanceOf(InsufficientBalanceException.class);
    }
}