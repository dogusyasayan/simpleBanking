package com.eteration.simplebanking.services;

import com.eteration.simplebanking.builder.AccountBuilder;
import com.eteration.simplebanking.converter.AccountConverter;
import com.eteration.simplebanking.converter.TransactionStrategy;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.mapper.TransactionStrategyMapper;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.enums.TransactionType;
import com.eteration.simplebanking.model.request.AccountTransactionRequest;
import com.eteration.simplebanking.model.request.BillPaymentRequest;
import com.eteration.simplebanking.model.request.CreateAccountRequest;
import com.eteration.simplebanking.model.request.DepositRequest;
import com.eteration.simplebanking.model.request.WithdrawalRequest;
import com.eteration.simplebanking.model.transaction.Transaction;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountConverter accountConverter;
    @Mock
    private AccountBuilder accountBuilder;

    @Mock
    private TransactionStrategyMapper transactionStrategyMapper;

    @Test
    void it_should_get_account() {
        //given
        Account account = Account.builder()
                .id(123L)
                .build();

        given(accountRepository.findById(123L)).willReturn(Optional.of(account));

        //when
        accountService.getAccount(123L);

        //then
        verify(accountConverter).apply(account);

    }

    @Test
    void it_should_get_account_is_empty() {
        //given
        given(accountRepository.findById(123L)).willReturn(Optional.empty());

        //when
        AccountNotFoundException thrown = (AccountNotFoundException) catchThrowable(() -> accountService.getAccount(123L));

        // then
        assertThat(thrown).isInstanceOf(AccountNotFoundException.class);

    }

    @Test
    void it_should_create_account() {
        //given
        CreateAccountRequest createAccountRequest = CreateAccountRequest.builder()
                .owner("customer")
                .build();

        Account account = Account.builder().build();


        given(accountBuilder.buildAccount(createAccountRequest)).willReturn(account);

        //when
        accountService.createAccount(createAccountRequest);

        //then
        verify(accountRepository).save(account);
    }

    @Test
    void it_should_delete_account() {
        //given
        Account account = Account.builder()
                .id(123L)
                .build();

        given(accountRepository.findById(123L)).willReturn(Optional.of(account));

        //when
        accountService.deleteAccount(123L);

        //then
        verify(accountRepository).delete(account);
    }

    @Test
    void it_should_credit() {
        // given
        Account account = Account.builder().build();
        DepositRequest depositRequest = DepositRequest.builder().build();
        TransactionStrategy strategy = mock(TransactionStrategy.class);
        given(transactionStrategyMapper.getStrategy(TransactionType.DEPOSIT)).willReturn(strategy);
        given(accountRepository.findById(123L)).willReturn(Optional.of(account));

        // when
        accountService.credit(depositRequest, 123L);

        // then
        verify(accountRepository).save(account);
    }

    @Test
    void it_should_debit() {
        // given
        Account account = Account.builder().build();
        WithdrawalRequest withdrawalRequest = WithdrawalRequest.builder().build();
        TransactionStrategy strategy = mock(TransactionStrategy.class);
        given(transactionStrategyMapper.getStrategy(TransactionType.WITHDRAWAL)).willReturn(strategy);
        given(accountRepository.findById(123L)).willReturn(Optional.of(account));

        // when
        accountService.debit(withdrawalRequest, 123L);

        // then
        verify(accountRepository).save(account);
    }

    @Test
    void it_should_bill_payment() {
        // given
        Account account = Account.builder().build();
        BillPaymentRequest billPaymentRequest = BillPaymentRequest.builder().build();
        TransactionStrategy strategy = mock(TransactionStrategy.class);
        given(transactionStrategyMapper.getStrategy(TransactionType.PAYMENT)).willReturn(strategy);
        given(accountRepository.findById(123L)).willReturn(Optional.of(account));

        // when
        accountService.billPayment(billPaymentRequest, 123L);

        // then
        verify(accountRepository).save(account);
    }

    @Test
    void it_should_perform_transaction() {
        // given
        Account account = Account.builder().build();
        AccountTransactionRequest accountTransactionRequest = new AccountTransactionRequest();
        TransactionStrategy strategy = mock(TransactionStrategy.class);
        Transaction transaction = mock(Transaction.class);
        given(transactionStrategyMapper.getStrategy(TransactionType.DEPOSIT)).willReturn(strategy);
        given(strategy.createTransaction(account, accountTransactionRequest)).willReturn(transaction);
        given(accountRepository.findById(123L)).willReturn(Optional.of(account));

        // when
        accountService.performTransaction(accountTransactionRequest, 123L, TransactionType.DEPOSIT);

        // then
        verify(accountRepository).save(account);
        verify(transactionRepository).save(transaction);
    }
}