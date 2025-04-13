package com.get.automation.utils;


import java.security.SecureRandom;
import java.util.Random;

public class RandomStringGenerator {
    /**
     * 生成随机短字符串
     * @param length 字符串长度（6-20字符）
     * @return 随机字符串
     */
        private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        private static final Random RANDOM = new Random();

        public static String generateRandomString(int length) {
            if (length < 3 || length > 6) {
                throw new IllegalArgumentException("长度必须在3-6之间");
            }

            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int randomIndex = RANDOM.nextInt(LETTERS.length());
                sb.append(LETTERS.charAt(randomIndex));
            }
            return sb.toString();
        }

}

