package com.example.event_managment.admin.service.impl;

import com.example.event_managment.admin.dto.response.SocialMediaSourceResponse;
import com.example.event_managment.entity.SocialMediaSource;
import com.example.event_managment.admin.repository.ISocialMediaSourceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialMediaSourcesService {

    @Autowired
    ISocialMediaSourceRepo iSocialMediaSourceRepo;

    public List<SocialMediaSourceResponse> getAllSocialSources()
    {
        List<SocialMediaSource> socialMediaSourceRes = iSocialMediaSourceRepo.findAll();
        return socialMediaSourceRes.stream().map(this::createResponse).toList();
    }

    private SocialMediaSourceResponse createResponse(SocialMediaSource socialMediaSource)
    {
        return new SocialMediaSourceResponse(
                socialMediaSource.getId(),
                socialMediaSource.getName()
        );
    }
}
