package com.example.event_management.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.event_management.admin.dto.response.EventAccessTypeResponse;
import com.example.event_management.entity.Event;
import com.example.event_management.entity.EventAccessType;
import com.example.event_management.repository.IEventAccessTypeRepo;
import com.example.event_management.repository.IEventMemberAccessRepo;
import com.example.event_management.repository.IEventRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventAccessService {

    @Autowired
    IEventRepo iEventRepo;

    @Autowired
    IEventAccessTypeRepo iEventAccessType;

    @Autowired
    IEventMemberAccessRepo iEventMemberAccess;

    public List<EventAccessTypeResponse> eventAllAccess(Long eventId) {
        Event event = iEventRepo.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
        List<EventAccessType> allAccess = iEventAccessType.findByEventId(event);

        return allAccess.stream().map(this::createResponse).toList();

    }

    public String deleteAccessTypeData(Long accessTypeId) {
        // EventAccessType eventAccessType =
        // iEventAccessType.findById(accessTypeId).orElseThrow(
        // () -> new EntityNotFoundException("Access Type Not Found")
        // );
        iEventAccessType.findById(accessTypeId).orElseThrow(
                () -> new EntityNotFoundException("Access Type Not Found"));
        iEventMemberAccess.deleteByAccessTypeId(accessTypeId);
        iEventAccessType.deleteById(accessTypeId);

        return "Event Access type Deleted Successfully";
    }

    private EventAccessTypeResponse createResponse(EventAccessType eventAccessType) {
        return new EventAccessTypeResponse(
                eventAccessType.getId(),
                eventAccessType.getEventAccessTypeName());
    }
}
