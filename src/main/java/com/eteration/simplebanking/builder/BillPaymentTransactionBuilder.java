package com.eteration.simplebanking.builder;

import com.eteration.simplebanking.converter.TransactionStrategy;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.enums.ErrorStatus;
import com.eteration.simplebanking.model.enums.TransactionStatus;
import com.eteration.simplebanking.model.enums.TransactionType;
import com.eteration.simplebanking.model.request.BillPaymentRequest;
import com.eteration.simplebanking.model.transaction.BillPaymentTransaction;
import com.eteration.simplebanking.model.transaction.Transaction;
import com.eteration.simplebanking.utils.NumberGenerator;
import org.springframework.stereotype.Component;

@Component
public class BillPaymentTransactionBuilder implements TransactionStrategy<BillPaymentRequest> {

    @Override
    public Transaction createTransaction(Account account, BillPaymentRequest billPaymentRequest) {
        if (billPaymentRequest.getAmount() > account.getBalance()) {
            throw new InsufficientBalanceException(ErrorStatus.INSUFFICIENT_BALANCE);
        }

        double newBalance = account.getBalance() - billPaymentRequest.getAmount();
        account.setBalance(newBalance);

        BillPaymentTransaction transaction = new BillPaymentTransaction();
        transaction.setAccount(account);
        transaction.setAmount(billPaymentRequest.getAmount());
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setApprovalCode(NumberGenerator.generateApprovalCode());
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setBillNumber(billPaymentRequest.getBillNumber());
        transaction.setBillerName(billPaymentRequest.getBillerName());
        return transaction;
    }
}