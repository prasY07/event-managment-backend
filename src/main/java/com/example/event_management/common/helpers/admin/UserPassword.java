package com.example.event_management.common.helpers.admin;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserPassword {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 8;
    private static final SecureRandom random = new SecureRandom();
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Generates a temporary password
    public static String createUserTempPassword() {
        StringBuilder tempPassword = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            tempPassword.append(CHARACTERS.charAt(index));
        }
        return tempPassword.toString();
    }

    // Generates a hashed password from a temporary one
    public static String createAndHashPassword() {
        // String rawPassword = createUserTempPassword();
        String rawPassword = "password1@";
        return passwordEncoder.encode(rawPassword);
    }

}
