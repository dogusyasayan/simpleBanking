package com.eteration.simplebanking.mapper;

import com.eteration.simplebanking.builder.BillPaymentTransactionBuilder;
import com.eteration.simplebanking.builder.DepositTransactionBuilder;
import com.eteration.simplebanking.builder.WithdrawalTransactionBuilder;
import com.eteration.simplebanking.converter.TransactionStrategy;
import com.eteration.simplebanking.model.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionStrategyMapper {

    private final Map<TransactionType, TransactionStrategy> strategies = new HashMap<>();

    @Autowired
    public TransactionStrategyMapper(DepositTransactionBuilder depositTransactionBuilder,
                                      WithdrawalTransactionBuilder withdrawalTransactionBuilder,
                                      BillPaymentTransactionBuilder billPaymentTransactionBuilder) {
        strategies.put(TransactionType.DEPOSIT, depositTransactionBuilder);
        strategies.put(TransactionType.WITHDRAWAL, withdrawalTransactionBuilder);
        strategies.put(TransactionType.PAYMENT, billPaymentTransactionBuilder);
    }

    public TransactionStrategy getStrategy(TransactionType type) {
        return strategies.get(type);
    }
}

