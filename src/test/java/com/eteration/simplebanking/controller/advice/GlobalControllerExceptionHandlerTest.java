package com.eteration.simplebanking.controller.advice;

import com.eteration.simplebanking.DemoApplication;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.enums.ErrorStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TestController.class)
@ContextConfiguration(classes = {DemoApplication.class})
class GlobalControllerExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void it_should_respond_with_404_forAccountNotFoundException() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get("/account-not-found"));

        //then
        resultActions
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception", is("AccountNotFoundException")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    void it_should_respond_with_404_for_InsufficientBalanceException() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get("/insufficient-balance"));

        //then
        resultActions
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception", is("InsufficientBalanceException")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }
}

@RestController
class TestController {
    @GetMapping("/account-not-found")
    public void AccountNotFoundException() {
        throw new AccountNotFoundException(ErrorStatus.ACCOUNT_NOT_FOUND);
    }

    @GetMapping("/insufficient-balance")
    public void InsufficientBalanceException() {
        throw new InsufficientBalanceException(ErrorStatus.INSUFFICIENT_BALANCE);
    }
}