package com.eteration.simplebanking.services;


import com.eteration.simplebanking.builder.AccountBuilder;
import com.eteration.simplebanking.converter.AccountConverter;
import com.eteration.simplebanking.converter.TransactionStrategy;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.mapper.TransactionStrategyMapper;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.enums.ErrorStatus;
import com.eteration.simplebanking.model.enums.TransactionType;
import com.eteration.simplebanking.model.request.AccountTransactionRequest;
import com.eteration.simplebanking.model.request.CreateAccountRequest;
import com.eteration.simplebanking.model.request.DepositRequest;
import com.eteration.simplebanking.model.request.WithdrawalRequest;
import com.eteration.simplebanking.model.response.AccountResponse;
import com.eteration.simplebanking.model.response.AccountTransactionResponse;
import com.eteration.simplebanking.model.transaction.Transaction;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccountConverter accountConverter;
    private final AccountBuilder accountBuilder;
    private final TransactionStrategyMapper transactionStrategyMapper;

    public AccountResponse getAccount(Long accountId) {
        Account account = getAccountById(accountId);
        return accountConverter.apply(account);
    }

    @Transactional
    public String createAccount(CreateAccountRequest createAccountRequest) {
        Account newAccount = accountBuilder.buildAccount(createAccountRequest);
        accountRepository.save(newAccount);
        return "Account created successfully with account number: " + newAccount.getAccountNumber();
    }

    @Transactional
    public String deleteAccount(Long accountId) {
        Account account = getAccountById(accountId);
        accountRepository.delete(account);
        return "Account deleted successfully with account number: " + account.getAccountNumber();
    }


    // Deposit operation
    @Transactional
    public AccountTransactionResponse credit(DepositRequest depositRequest, Long accountId) {
        return performTransaction(depositRequest, accountId, TransactionType.DEPOSIT);
    }

    // Withdrawal operation
    @Transactional
    public AccountTransactionResponse debit(WithdrawalRequest withdrawalRequest, Long accountId) {
        return performTransaction(withdrawalRequest, accountId, TransactionType.WITHDRAWAL);
    }

    // Bill payment operation
    @Transactional
    public AccountTransactionResponse billPayment(AccountTransactionRequest accountTransactionRequest, Long accountId) {
        return performTransaction(accountTransactionRequest, accountId, TransactionType.PAYMENT);
    }

    // Common method to execute transactions
    @Transactional
    public AccountTransactionResponse performTransaction(AccountTransactionRequest accountTransactionRequest, Long accountId, TransactionType transactionType) {
        Account account = getAccountById(accountId);
        TransactionStrategy strategy = transactionStrategyMapper.getStrategy(transactionType);
        Transaction transaction = strategy.createTransaction(account, accountTransactionRequest);
        accountRepository.save(account);
        transactionRepository.save(transaction);
        return accountConverter.apply(account, transaction);
    }

    private Account getAccountById(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(ErrorStatus.ACCOUNT_NOT_FOUND);
        }
        return optionalAccount.get();
    }
}
