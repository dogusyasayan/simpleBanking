package com.eteration.simplebanking.converter;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.transaction.Transaction;

public interface TransactionStrategy<T> {

    Transaction createTransaction(Account account, T model);

}
