package com.get.automation.utils;


import java.security.SecureRandom;

public class RandomStringGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成随机短字符串
     * @param length 字符串长度（6-20字符）
     * @return 随机字符串
     */
    public static String generateRandomString(int length) {
        if (length < 3 || length > 6) {
            throw new IllegalArgumentException("长度必须在3-6之间");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}

