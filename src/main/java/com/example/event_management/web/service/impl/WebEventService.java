package com.example.event_management.web.service.impl;

import com.example.event_management.entity.EventMemberType;
import com.example.event_management.repository.IEventMemberTypeRepo;
import com.example.event_management.web.dto.response.WebEventMemberListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.event_management.common.AppStatus;
import com.example.event_management.common.exception.CustomException;
import com.example.event_management.entity.Event;
import com.example.event_management.repository.IEventRepo;
import com.example.event_management.web.dto.response.WebEventShortResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WebEventService {

    @Autowired
    IEventRepo iEventRepo;

    @Autowired
    IEventMemberTypeRepo iEventMemberTypeRepo;

    public WebEventShortResponse getInfo(String eventId)
    {

        Event res = iEventRepo.findEventByUUID(eventId);
        if(res == null)
        {
            throw new CustomException("Event Not Found",HttpStatus.NOT_FOUND);
        }

//        if(res.getStatus() == AppStatus.EStatus.INACTIVE)
//        {
//            throw new CustomException("Event Not Started",HttpStatus.CONFLICT);
//        }

        return eventShortResponse(res);
    }

    public List<WebEventMemberListResponse> eventAllMembers(String eventId)
    {
        Event event = iEventRepo.findEventByUUID(eventId);
        if(event == null)
        {
            throw new CustomException("Event Not Found",HttpStatus.NOT_FOUND);
        }
        List<EventMemberType> res =   iEventMemberTypeRepo.findByEventId(event);


        return res.stream()
                .map(this::eventMemberList)
                .collect(Collectors.toList());
    }

    private WebEventMemberListResponse eventMemberList(EventMemberType eventMemberList)
    {

        return new WebEventMemberListResponse(
                eventMemberList.getId(),
                eventMemberList.getMemberTypeName(),
                eventMemberList.getEntryFees()

        );
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

