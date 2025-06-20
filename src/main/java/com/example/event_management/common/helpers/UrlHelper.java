package com.example.event_management.common.helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class UrlHelper {

    public static String getBaseUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
    }

    public static String buildFullPath(String path) {
        return getBaseUrl() + path;
    }

    public static String imageUrl(String path) {
        return getBaseUrl() + '/' + path;
    }
}
