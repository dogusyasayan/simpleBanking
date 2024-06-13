package com.eteration.simplebanking.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorStatus {

    ACCOUNT_NOT_FOUND,
    INSUFFICIENT_BALANCE;
}
