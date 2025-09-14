package com.example.event_management.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.event_management.common.AppStatus;
import com.example.event_management.common.exception.CustomException;
import com.example.event_management.entity.Event;
import com.example.event_management.repository.IEventRepo;
import com.example.event_management.web.dto.response.WebEventShortResponse;

import java.util.Optional;

@Service
public class WebEventService {

    @Autowired
    IEventRepo iEventRepo;

    public WebEventShortResponse getInfo(String eventId)
    {

        Event res = iEventRepo.findEventByUUID(eventId);

//        if(res.getStatus() == AppStatus.EStatus.INACTIVE)
//        {
//            throw new CustomException("Event Not Started",HttpStatus.CONFLICT);
//        }

        return eventShortResponse(res);
    }

    private WebEventShortResponse eventShortResponse(Event event)
    {
     
        return new WebEventShortResponse(
            // event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getPrivacyPolicy(),
            event.getStartDate(),
            event.getEndDate(),
            event.getEventStartTime(),
            event.getEventEndTime(),
            event.getSponsoredBy()
        );
    }
    
}

