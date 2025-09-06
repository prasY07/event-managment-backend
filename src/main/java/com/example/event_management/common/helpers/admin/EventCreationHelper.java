package com.example.event_management.common.helpers.admin;

import com.example.event_management.admin.dto.EventDto;
import com.example.event_management.common.AppStatus;
import com.example.event_management.entity.*;
import com.example.event_management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class EventCreationHelper {

    @Autowired
    IEventRepo iEventRepo;

    @Autowired
    IEventMemberTypeRepo iEventMemberType;

    @Autowired
    IEventAccessTypeRepo iEventAccessType;

    @Autowired
    IEventDayRepository iEventDayRepository;

    @Autowired
    EventHelper eventHelper;

    @Autowired
    IEventServiceRepo iEventServiceRepo;

    public Event addEvent(EventDto eventDto, User user)
    {
        Event saveEvent = createEvent(eventDto,user);
        // Add Event Member Type
        Boolean eventMemberType = addEventMemberType(eventDto,saveEvent);

        // Add Event Access Type
        Boolean eventAccessType = addAccessType(eventDto,saveEvent);

        // Add Event Days
        Boolean addEventDays = createEventDay(eventDto,saveEvent);

        // Add Event Service
        Boolean addService = createEventService(eventDto,saveEvent);

        return  saveEvent;

    }

    private Event createEvent(EventDto eventDto, User user)
    {
        String uniqueEventId = UUID.randomUUID().toString(); // or your custom logic

        // Create and populate Event entity
        Event event = new Event();
        event.setTitle(eventDto.getTitle());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        event.setRegistrationEndDate(eventDto.getRegistrationEndDate());
        event.setVenue(eventDto.getVenue());
        event.setAddress(eventDto.getAddress());
        event.setCategory(eventDto.getCategory());
        event.setDescription(eventDto.getDescription());
        event.setPrivacyPolicy(eventDto.getPrivacyPolicy());
        event.setEventStartTime(LocalTime.parse(eventDto.getEventStartTime()));
        event.setEventEndTime(LocalTime.parse(eventDto.getEventEndTime()));
        event.setEventStatus(AppStatus.EventStatus.UPCOMING);
        event.setUser(user);
        event.setEventId(uniqueEventId);

        // Save to DB
        return iEventRepo.save(event);
    }

    private Boolean addEventMemberType(EventDto eventDto,Event savedEvent)
    {
        List<String> memberTypes = com.example.event_management.common.helpers.admin.EventHelper.parseUniqueCommaSeparatedValues(eventDto.getEventMemberType());
        for (String memberTypeName : memberTypes) {
            EventMemberType eventMemberType = new EventMemberType();
            eventMemberType.setEventId(savedEvent); // Set full Event object
            eventMemberType.setMemberTypeName(memberTypeName);
            iEventMemberType.save(eventMemberType);
        }
        return Boolean.TRUE;
    }

    private Boolean addAccessType(EventDto eventDto,Event savedEvent)
    {
        List<String> accessTypes = EventHelper.parseUniqueCommaSeparatedValues(eventDto.getEventAccessType());
        for (String accessType : accessTypes) {
            EventAccessType eventAccessType = new EventAccessType();
            eventAccessType.setEventId(savedEvent); // Set full Event object
            eventAccessType.setEventAccessTypeName(accessType);
            iEventAccessType.save(eventAccessType);
        }
        return Boolean.TRUE;
    }

    private Boolean createEventDay(EventDto eventDto, Event savedEvent)
    {
        // generate days between start and end
        LocalDate currentDate = eventDto.getStartDate();
        List<EventDay> eventDays = new ArrayList<>();

        while (!currentDate.isAfter(eventDto.getEndDate())) {
            EventDay eventDay = new EventDay();
            eventDay.setEvent(savedEvent);
            eventDay.setEventDate(currentDate);
            eventDays.add(eventDay);

            currentDate = currentDate.plusDays(1);
        }

        // save all event days
        iEventDayRepository.saveAll(eventDays);

        return Boolean.TRUE;
    }

    private Boolean createEventService(EventDto eventDto, Event savedEvent)
    {
        List<String> accessTypes = EventHelper.parseUniqueCommaSeparatedValues(eventDto.getEventServices());
        for (String accessType : accessTypes) {
            EventService eventService = new EventService();
            eventService.setEventId(savedEvent); // Set full Event object
            eventService.setServiceName(accessType);
            iEventServiceRepo.save(eventService);
        }
        return Boolean.TRUE;

    }
}
