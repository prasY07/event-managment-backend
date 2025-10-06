package com.example.event_management.common.helpers.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.event_management.admin.dto.CreateEventDto;
import com.example.event_management.common.AppStatus;
import com.example.event_management.entity.Event;
import com.example.event_management.entity.EventAccessType;
import com.example.event_management.entity.EventDay;
import com.example.event_management.entity.EventMemberType;
import com.example.event_management.entity.EventService;
import com.example.event_management.entity.User;
import com.example.event_management.repository.IEventAccessTypeRepo;
import com.example.event_management.repository.IEventDayRepo;
import com.example.event_management.repository.IEventMemberTypeRepo;
import com.example.event_management.repository.IEventRepo;
import com.example.event_management.repository.IEventServiceRepo;

import jakarta.transaction.Transactional;

@Component
public class EventCreationHelper {

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

    @Transactional
    public Event addEvent(CreateEventDto eventDto, User user)
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


    private Event createEvent(CreateEventDto eventDto, User user)
    {

        String eventId;
        do {
            eventId = EventHelper.createUniqueEventID();
        } while (iEventRepo.existsByEventId(eventId));

                System.out.println("eventId--"+eventId);

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
        event.setSponsoredBy(eventDto.getSponsoredBy()) ;
        event.setIsFoc(eventDto.getIsFoc());
        event.setUser(user);
        event.setEventId(eventId);
        event.setRegistrationStartDate(eventDto.getRegistrationStartDate());

        // Save to DB
        return iEventRepo.save(event);
    }

    private Boolean addEventMemberType(CreateEventDto eventDto,Event savedEvent)
    {

        List<String> memberTypes = EventHelper.parseUniqueCommaSeparatedValues(eventDto.getEventMemberType());
        for (String memberTypeName : memberTypes) {
            EventMemberType eventMemberType = new EventMemberType();
            eventMemberType.setEventId(savedEvent); // Set full Event object
            eventMemberType.setMemberTypeName(memberTypeName);
            iEventMemberType.save(eventMemberType);
        }

        return Boolean.TRUE;
    }

    private Boolean addAccessType(CreateEventDto eventDto,Event savedEvent)
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

    private Boolean createEventDay(CreateEventDto eventDto, Event savedEvent)
    {

        // generate days between start and end
        LocalDate currentDate = eventDto.getStartDate();
        // List<EventDay> eventDays = new ArrayList<>();

        while (!currentDate.isAfter(eventDto.getEndDate())) {
            EventDay eventDay = new EventDay();
            eventDay.setEvent(savedEvent);
            eventDay.setEventDate(currentDate);
            // eventDays.add(eventDay);
             iEventDayRepo.save(eventDay);

            currentDate = currentDate.plusDays(1);
        }

                        System.out.println("hhere661322");

        // save all event days
                        System.out.println("hhere6612322");

        return Boolean.TRUE;
    }

    private Boolean createEventService(CreateEventDto eventDto, Event savedEvent)
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
