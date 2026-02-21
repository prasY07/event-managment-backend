package com.example.event_management.commonapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.commonapi.dto.response.SocialMediaSourceResponse;
import com.example.event_management.commonapi.service.impl.SocialMediaSourcesService;

@RestController
@RequestMapping("/api/common/social-media")
public class SocialMediaSourceController {

    @Autowired
    SocialMediaSourcesService socialMediaSourcesService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<SocialMediaSourceResponse>>> allSocialMediaSources() {
        List<SocialMediaSourceResponse> allSources = socialMediaSourcesService.getAllSocialSources();
        return ApiResponse.success("Social sources list", allSources);
    }
}
