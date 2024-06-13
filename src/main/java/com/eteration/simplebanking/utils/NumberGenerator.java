package com.eteration.simplebanking.utils;

import java.util.Random;

public class NumberGenerator {
    private static final int PREFIX_DIGIT_COUNT = 3;
    private static final int SUFFIX_DIGIT_COUNT = 4;

    private static final int CODE_LENGTH = 36;

    public static String generateAccountNumber() {
        String prefix = generateRandomNumber(PREFIX_DIGIT_COUNT);
        String suffix = generateRandomNumber(SUFFIX_DIGIT_COUNT);
        return prefix + "-" + suffix;
    }

    public static String generateApprovalCode() {
        StringBuilder codeBuilder = new StringBuilder();
        Random random = new Random();

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < CODE_LENGTH; i++) {
            if (i == 8 || i == 13 || i == 18 || i == 23) {
                codeBuilder.append("-");
            } else {
                codeBuilder.append(characters.charAt(random.nextInt(characters.length())));
            }
        }

        return codeBuilder.toString();
    }

    private static String generateRandomNumber(int digitCount) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < digitCount; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}