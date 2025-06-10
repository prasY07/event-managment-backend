package com.example.event_managment.common.helpers;

import org.springframework.security.crypto.password.PasswordEncoder;

public class UserHelper {

    private final PasswordEncoder passwordEncoder;

    public UserHelper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String createAndHashPassword() {
        String rawPassword = createUserTempPassword();
        return passwordEncoder.encode(rawPassword);
    }

    public static String generateUserRegId() {
        return "USER-" + System.currentTimeMillis();
    }

    public static String createUserTempPassword() {
        StringBuilder tempPassword = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * characters.length());
            tempPassword.append(characters.charAt(index));
        }
        return tempPassword.toString();
    }

}
