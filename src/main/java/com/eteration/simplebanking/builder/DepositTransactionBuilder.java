package com.eteration.simplebanking.builder;

import com.eteration.simplebanking.converter.TransactionStrategy;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.enums.TransactionStatus;
import com.eteration.simplebanking.model.enums.TransactionType;
import com.eteration.simplebanking.model.request.DepositRequest;
import com.eteration.simplebanking.model.transaction.DepositTransaction;
import com.eteration.simplebanking.model.transaction.Transaction;
import com.eteration.simplebanking.utils.NumberGenerator;
import org.springframework.stereotype.Component;

@Component
public class DepositTransactionBuilder implements TransactionStrategy<DepositRequest> {

    @Override
    public Transaction createTransaction(Account account, DepositRequest depositRequest) {
        double newBalance = account.getBalance() + depositRequest.getAmount();
        account.setBalance(newBalance);

        DepositTransaction transaction = new DepositTransaction();
        transaction.setAccount(account);
        transaction.setAmount(depositRequest.getAmount());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setApprovalCode(NumberGenerator.generateApprovalCode());
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setDescription(depositRequest.getDescription());

        return transaction;
    }
}