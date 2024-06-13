package com.eteration.simplebanking.model.enums;

import lombok.Getter;

@Getter
public enum TransactionType {
    DEPOSIT("DepositTransaction"),
    WITHDRAWAL("WithdrawalTransaction"),
    PAYMENT("BillPaymentTransaction");

    private final String value;

    TransactionType(String value) {
        this.value = value;
    }
}