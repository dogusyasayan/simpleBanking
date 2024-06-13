package com.eteration.simplebanking.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

    private String accountNumber;

    private String owner;

    private Double balance;

    private LocalDateTime createdDate;

    private List<TransactionResponse> transactionResponses;
}
