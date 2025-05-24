package com.example.event_managment.admin.controller;

import com.example.event_managment.admin.dto.response.SocialMediaSourceResponse;
import com.example.event_managment.admin.service.impl.SocialMediaSourcesService;
import com.example.event_managment.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/social-media")
public class SocialMediaSourceController {

    @Autowired
    SocialMediaSourcesService socialMediaSourcesService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<SocialMediaSourceResponse>>> allSocialMediaSources()
    {
        List<SocialMediaSourceResponse> allSources = socialMediaSourcesService.getAllSocialSources();
        return ApiResponse.success("Social sources list", allSources);
    }

}
