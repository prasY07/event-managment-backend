package com.example.event_management.admin.service.impl;

import com.example.event_management.admin.dto.response.EventServiceResponse;
import com.example.event_management.entity.*;
import com.example.event_management.entity.EventService;
import com.example.event_management.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.event_management.admin.dto.AssignEventServiceDayDto;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;


@Service
public class EventDayService {

    @Autowired
    IEventRepo iEventRepo;

    @Autowired
    IMemberTypeServiceAccessRepo iMemberTypeServiceAccessRepo;

    @Autowired
    IEventDayRepository iEventDayRepository;

    @Autowired
    IEventServiceRepo iEventServiceRepo;

    @Autowired
    IEventMemberTypeRepo iEventMemberTypeRepo;




    public List<EventServiceResponse> getAllEventService() {
        List<EventService> eventService = iEventServiceRepo.findAll();
        return eventService.stream().map(this::createServiceResponse).toList();
    }


    @Transactional
    public Boolean assignServiceDaysToMember(AssignEventServiceDayDto assignEventServiceDayDto)
    {
        Event event = iEventRepo.findById(assignEventServiceDayDto.getEventId()).
                orElseThrow(() -> new EntityNotFoundException("Event not found"));

        EventDay eventDay = iEventDayRepository.findById(assignEventServiceDayDto.getDayId()).
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


       
   

}
