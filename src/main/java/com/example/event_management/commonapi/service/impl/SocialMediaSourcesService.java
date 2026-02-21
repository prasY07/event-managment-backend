package com.example.event_management.commonapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.event_management.commonapi.dto.response.SocialMediaSourceResponse;
import com.example.event_management.entity.SocialMediaSource;
import com.example.event_management.repository.ISocialMediaSourceRepo;

@Service
public class SocialMediaSourcesService {
    @Autowired
    ISocialMediaSourceRepo iSocialMediaSourceRepo;

    public List<SocialMediaSourceResponse> getAllSocialSources() {
        List<SocialMediaSource> socialMediaSourceRes = iSocialMediaSourceRepo.findAll();
        return socialMediaSourceRes.stream().map(this::createResponse).toList();
    }

    private SocialMediaSourceResponse createResponse(SocialMediaSource socialMediaSource) {
        return new SocialMediaSourceResponse(
                socialMediaSource.getId(),
                socialMediaSource.getName());
    }
}
