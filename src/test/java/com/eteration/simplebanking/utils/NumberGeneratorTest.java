package com.eteration.simplebanking.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberGeneratorTest {

    @Test
    void generateAccountNumber_should_return_string_with_correct_format() {
        // when
        String accountNumber = NumberGenerator.generateAccountNumber();

        // then
        assertNotNull(accountNumber);
        assertTrue(accountNumber.matches("\\d{3}-\\d{4}"));
    }

    @Test
    void generateApprovalCode_should_return_string_with_correct_format() {
        // when
        String approvalCode = NumberGenerator.generateApprovalCode();

        // then
        assertNotNull(approvalCode);
        assertTrue(approvalCode.matches("[A-Za-z0-9]{8}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{12}"));
    }
}