package com.eteration.simplebanking.converter;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.enums.TransactionType;
import com.eteration.simplebanking.model.response.AccountResponse;
import com.eteration.simplebanking.model.response.AccountTransactionResponse;
import com.eteration.simplebanking.model.response.TransactionResponse;
import com.eteration.simplebanking.model.transaction.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountConverter {
    public AccountResponse apply(Account account) {

        List<TransactionResponse> transactionResponses = account.getTransactions().stream()
                .map(transaction -> TransactionResponse.builder()
                        .createdDate(transaction.getCreatedDate())
                        .amount(transaction.getAmount())
                        .transactionType(transaction.getTransactionType().getValue())
                        .approvalCode(transaction.getApprovalCode())
                        .build())
                .collect(Collectors.toList());

        return AccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .owner(account.getOwner())
                .createdDate(account.getCreatedDate())
                .transactionResponses(transactionResponses)
                .build();
    }

    public AccountTransactionResponse apply(Account account, Transaction transaction) {
        double previousBalance = account.getBalance();
        double transactionAmount = transaction.getAmount();
        double newBalance;

        if (transaction.getTransactionType() == TransactionType.DEPOSIT) {
            newBalance = previousBalance + transactionAmount;
        } else {
            newBalance = previousBalance - transactionAmount;
        }

        return AccountTransactionResponse.builder()
                .previousBalance("Previous Balance: " + previousBalance)
                .transactionAmount("Transaction Amount: " + transactionAmount)
                .currentBalance("Current Balance: " + newBalance)
                .build();
    }

}
