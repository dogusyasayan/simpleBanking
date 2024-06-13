package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.request.CreateAccountRequest;
import com.eteration.simplebanking.model.request.DepositRequest;
import com.eteration.simplebanking.model.request.WithdrawalRequest;
import com.eteration.simplebanking.services.AccountService;
import com.eteration.simplebanking.testutils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void it_should_create_account() throws Exception {
        //given
        CreateAccountRequest request = CreateAccountRequest.builder().build();

        //when
        ResultActions resultActions = mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(request)));

        //then
        resultActions.andExpect(status().isCreated());
        ArgumentCaptor<CreateAccountRequest> argumentCaptor = ArgumentCaptor.forClass(CreateAccountRequest.class);
        verify(accountService).createAccount(argumentCaptor.capture());
    }

    @Test
    void it_should_delete_account() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(delete("/account/v1/123"));

        //then
        resultActions.andExpect(status().isOk());
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(accountService).deleteAccount(argumentCaptor.capture());
        Long value = argumentCaptor.getValue();
        assertThat(value).isEqualTo(123L);
    }

    @Test
    void it_should_get_account() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get("/account/v1/123"));

        //then
        resultActions.andExpect(status().isOk());
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(accountService).getAccount(argumentCaptor.capture());
        Long value = argumentCaptor.getValue();
        assertThat(value).isEqualTo(123L);
    }

    @Test
    void it_should_credit() throws Exception {
        //given
        DepositRequest depositRequest = DepositRequest.builder().build();

        //when
        ResultActions resultActions = mockMvc.perform(post("/account/v1/credit/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(depositRequest)));

        //then
        resultActions.andExpect(status().isOk());
        ArgumentCaptor<DepositRequest> argumentCaptor1 = ArgumentCaptor.forClass(DepositRequest.class);
        ArgumentCaptor<Long> argumentCaptor2 = ArgumentCaptor.forClass(Long.class);
        verify(accountService).credit(argumentCaptor1.capture(), argumentCaptor2.capture());
        Long value = argumentCaptor2.getValue();
        assertThat(value).isEqualTo(123L);
    }

    @Test
    void it_should_debit() throws Exception {
        //given
        WithdrawalRequest withdrawalRequest = WithdrawalRequest.builder().build();

        //when
        ResultActions resultActions = mockMvc.perform(post("/account/v1/debit/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(withdrawalRequest)));

        //then
        resultActions.andExpect(status().isOk());
        ArgumentCaptor<WithdrawalRequest> argumentCaptor1 = ArgumentCaptor.forClass(WithdrawalRequest.class);
        ArgumentCaptor<Long> argumentCaptor2 = ArgumentCaptor.forClass(Long.class);
        verify(accountService).debit(argumentCaptor1.capture(), argumentCaptor2.capture());
        Long value = argumentCaptor2.getValue();
        assertThat(value).isEqualTo(123L);
    }
}