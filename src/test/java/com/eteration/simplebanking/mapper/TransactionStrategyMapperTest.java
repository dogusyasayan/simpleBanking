package com.eteration.simplebanking.mapper;

import com.eteration.simplebanking.builder.BillPaymentTransactionBuilder;
import com.eteration.simplebanking.builder.DepositTransactionBuilder;
import com.eteration.simplebanking.builder.WithdrawalTransactionBuilder;
import com.eteration.simplebanking.converter.TransactionStrategy;
import com.eteration.simplebanking.model.enums.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class TransactionStrategyMapperTest {

    @Mock
    private DepositTransactionBuilder depositTransactionBuilder;

    @Mock
    private WithdrawalTransactionBuilder withdrawalTransactionBuilder;

    @Mock
    private BillPaymentTransactionBuilder billPaymentTransactionBuilder;

    @InjectMocks
    private TransactionStrategyMapper transactionStrategyMapper;

    @Test
    void getStrategy_should_return_correct_strategy_for_each_transaction_type() {
        // given
        TransactionType depositType = TransactionType.DEPOSIT;
        TransactionType withdrawalType = TransactionType.WITHDRAWAL;
        TransactionType paymentType = TransactionType.PAYMENT;

        // when
        TransactionStrategy depositStrategy = transactionStrategyMapper.getStrategy(depositType);
        TransactionStrategy withdrawalStrategy = transactionStrategyMapper.getStrategy(withdrawalType);
        TransactionStrategy paymentStrategy = transactionStrategyMapper.getStrategy(paymentType);

        // then
        assertThat(depositStrategy).isEqualTo(depositTransactionBuilder);
        assertThat(withdrawalStrategy).isEqualTo(withdrawalTransactionBuilder);
        assertThat(paymentStrategy).isEqualTo(billPaymentTransactionBuilder);
    }
}