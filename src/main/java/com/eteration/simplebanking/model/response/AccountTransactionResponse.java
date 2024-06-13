package com.eteration.simplebanking.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountTransactionResponse {
    private String previousBalance;
    private String transactionAmount;
    private String currentBalance;
}
