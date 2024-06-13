package com.eteration.simplebanking.builder;

import com.eteration.simplebanking.converter.TransactionStrategy;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.enums.ErrorStatus;
import com.eteration.simplebanking.model.enums.TransactionStatus;
import com.eteration.simplebanking.model.enums.TransactionType;
import com.eteration.simplebanking.model.request.WithdrawalRequest;
import com.eteration.simplebanking.model.transaction.Transaction;
import com.eteration.simplebanking.model.transaction.WithdrawalTransaction;
import com.eteration.simplebanking.utils.NumberGenerator;
import org.springframework.stereotype.Component;

@Component
public class WithdrawalTransactionBuilder implements TransactionStrategy<WithdrawalRequest> {

    @Override
    public Transaction createTransaction(Account account, WithdrawalRequest withdrawalRequest) {
        if (withdrawalRequest.getAmount() > account.getBalance()) {
            throw new InsufficientBalanceException(ErrorStatus.INSUFFICIENT_BALANCE);
        }

        double newBalance = account.getBalance() - withdrawalRequest.getAmount();
        account.setBalance(newBalance);

        WithdrawalTransaction transaction = new WithdrawalTransaction();
        transaction.setAccount(account);
        transaction.setAmount(withdrawalRequest.getAmount());
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setApprovalCode(NumberGenerator.generateApprovalCode());
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setDescription(withdrawalRequest.getDescription());

        return transaction;
    }
}