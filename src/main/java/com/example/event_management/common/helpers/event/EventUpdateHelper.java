package com.example.event_management.common.helpers.event;

import com.example.event_management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class EventUpdateHelper {
    @Autowired
    IEventRepo iEventRepo;

    @Autowired
    IEventMemberTypeRepo iEventMemberType;

    @Autowired
    IEventAccessTypeRepo iEventAccessType;

    @Autowired
    IEventDayRepo iEventDayRepo;

    @Autowired
    IEventServiceRepo iEventServiceRepo;

//    @Transactional
//    public Event updatevent(CreateEventDto eventDto, User user)
//    {
//
//
//    }
}
