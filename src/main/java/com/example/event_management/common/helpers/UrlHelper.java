package com.example.event_management.common.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class UrlHelper {

    @Value("${app.base-url}")
    private String baseUrl;

    public String getBaseUrlWithForwardSlash() {
        return baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

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
