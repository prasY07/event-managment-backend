package com.example.event_management.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.event_management.admin.dto.AssignEventServiceDayDto;
import com.example.event_management.admin.dto.response.EventDayResponse;
import com.example.event_management.admin.dto.response.EventServiceResponse;
import com.example.event_management.entity.Event;
import com.example.event_management.entity.EventDay;
import com.example.event_management.entity.EventMemberType;
import com.example.event_management.entity.EventService;
import com.example.event_management.entity.MemberTypeServiceAccess;
import com.example.event_management.repository.IEventDayRepo;
import com.example.event_management.repository.IEventMemberTypeRepo;
import com.example.event_management.repository.IEventRepo;
import com.example.event_management.repository.IEventServiceRepo;
import com.example.event_management.repository.IMemberTypeServiceAccessRepo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service
public class EventDayService {

    @Autowired
    IEventRepo iEventRepo;

    @Autowired
    IMemberTypeServiceAccessRepo iMemberTypeServiceAccessRepo;

    @Autowired
    IEventServiceRepo iEventServiceRepo;

    @Autowired
    IEventMemberTypeRepo iEventMemberTypeRepo;

    @Autowired
    IEventDayRepo iEventDayRepo;




    public List<EventServiceResponse> getAllEventService(Long EventId) {
        Event event = iEventRepo.findById(EventId).
                orElseThrow(() -> new EntityNotFoundException("Event not found"));
        List<EventService> eventService = iEventServiceRepo.findByEventId(event);
        return eventService.stream().map(this::createServiceResponse).toList();
    }

    public List<EventDayResponse> getAllEventDay(Long EventId) {
        Event event = iEventRepo.findById(EventId).
                orElseThrow(() -> new EntityNotFoundException("Event not found"));
        List<EventDay> eventDay = iEventDayRepo.findByEvent(event);
        return eventDay.stream().map(this::createEventDayResponse).toList();
    }


    @Transactional
    public Boolean assignServiceDaysToMember(AssignEventServiceDayDto assignEventServiceDayDto)
    {
        Event event = iEventRepo.findById(assignEventServiceDayDto.getEventId()).
                orElseThrow(() -> new EntityNotFoundException("Event not found"));

        EventDay eventDay = iEventDayRepo.findById(assignEventServiceDayDto.getDayId()).
            orElseThrow(() -> new EntityNotFoundException("Event not found"));

        if(iMemberTypeServiceAccessRepo.findByEventIdAndDayId(event.getId(),eventDay.getDayId()) > 0)
        {
            iMemberTypeServiceAccessRepo.deleteByEventIdAndDayId(event.getId(),eventDay.getDayId());
        }

        for (Long serviceId : assignEventServiceDayDto.getServiceId())
        {
            EventService eventService = iEventServiceRepo.findById(serviceId).
                orElseThrow(() -> new EntityNotFoundException("Service not found"));

            MemberTypeServiceAccess memberTypeServiceAccess = new MemberTypeServiceAccess();
            for (Long memberTypeId : assignEventServiceDayDto.getMemberTypeId())
            {
                EventMemberType eventMemberType = iEventMemberTypeRepo.findById(memberTypeId).
                        orElseThrow(() -> new EntityNotFoundException("Member not found"));

                memberTypeServiceAccess.setEventId(event);
                memberTypeServiceAccess.setDayId(eventDay);
                memberTypeServiceAccess.setServiceId(eventService);
                memberTypeServiceAccess.setMemberType(eventMemberType);
                iMemberTypeServiceAccessRepo.save(memberTypeServiceAccess);
            }

        }
         return Boolean.TRUE;
    }

    private EventServiceResponse createServiceResponse(EventService eventService) {
        return new EventServiceResponse(
                eventService.getServiceId(),
                eventService.getServiceName()
        );
    }

        private EventDayResponse createEventDayResponse(EventDay eventDay) {
        return new EventDayResponse(
                eventDay.getDayId(),
                eventDay.getEventDate()
        );
    }


       
   
    

}
