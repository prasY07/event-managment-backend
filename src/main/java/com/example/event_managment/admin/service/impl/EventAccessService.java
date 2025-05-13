package com.example.event_managment.admin.service.impl;

import com.example.event_managment.admin.dto.response.EventAccessTypeResponse;
import com.example.event_managment.admin.dto.response.EventMemberTypeResponse;
import com.example.event_managment.admin.entity.Event;
import com.example.event_managment.admin.entity.EventAccessType;
import com.example.event_managment.admin.entity.EventMemberType;
import com.example.event_managment.admin.repository.IEventAccessType;
import com.example.event_managment.admin.repository.IEventMemberAccess;
import com.example.event_managment.admin.repository.IEventMemberType;
import com.example.event_managment.admin.repository.IEventRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventAccessService {

    @Autowired
    IEventRepo iEventRepo;

    @Autowired
    IEventAccessType iEventAccessType;


    @Autowired
    IEventMemberAccess iEventMemberAccess;

    public List<EventAccessTypeResponse> eventAllAccess(Long eventId)
    {
        Event event = iEventRepo.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
         List<EventAccessType> allAccess =  iEventAccessType.findByEventId(event);

         return allAccess.stream().map(this::createResponse).toList();

    }

    public String deleteAccessTypeData(Long accessTypeId)
    {
        EventAccessType eventAccessType = iEventAccessType.findById(accessTypeId).orElseThrow(
                () -> new EntityNotFoundException("Access Type Not Found")
        );
        iEventMemberAccess.deleteByAccessTypeId(accessTypeId);
        iEventAccessType.deleteById(accessTypeId);

        return "Event Access type Deleted Successfully";
    }

    private EventAccessTypeResponse createResponse(EventAccessType eventAccessType)
    {
        return new EventAccessTypeResponse(
                eventAccessType.getId(),
                eventAccessType.getEventAccessTypeName()
        );
    }
}
