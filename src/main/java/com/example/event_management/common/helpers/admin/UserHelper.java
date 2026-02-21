package com.example.event_management.common.helpers.admin;

public class UserHelper {

    public static String generateUserRegId() {
        return "USER-" + System.currentTimeMillis();
    }

}
