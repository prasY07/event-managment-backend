package com.example.event_managment.admin.service.impl;

import com.example.event_managment.admin.dto.EventFeesDto;
import com.example.event_managment.admin.dto.response.EventMemberTypeResponse;
import com.example.event_managment.entity.Event;
import com.example.event_managment.entity.EventMemberType;
import com.example.event_managment.admin.repository.IEventMemberAccessRepo;
import com.example.event_managment.admin.repository.IEventMemberTypeRepo;
import com.example.event_managment.admin.repository.IEventRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventMemberService {

    @Autowired
    IEventRepo iEventRepo;

    @Autowired
    IEventMemberTypeRepo iEventMemberType;

    @Autowired
    IEventMemberAccessRepo iEventMemberAccess;

    public List<EventMemberTypeResponse> eventAllMembers(Long eventId)
    {
        Event event = iEventRepo.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
         List<EventMemberType> allMembers =  iEventMemberType.findByEventId(event);

         return allMembers.stream().map(this::createResponse).toList();

    }

    public String deleteMemberData(Long memberId)
    {
        // EventMemberType eventMemberType = iEventMemberType.findById(memberId).orElseThrow(
        //         () -> new EntityNotFoundException("Member Not Found")
        // );
        iEventMemberType.findById(memberId).orElseThrow(
                () -> new EntityNotFoundException("Member Not Found")
        );
        iEventMemberAccess.deleteByMemberTypeId(memberId);
        iEventMemberType.deleteById(memberId);

        return "Member Deleted Successfully";
    }

    public String updateEventFees(Long eventId, EventFeesDto eventFeesDto){

        // Event event = iEventRepo.findById(eventId)
        //         .orElseThrow(() -> new EntityNotFoundException("Event not found"));

         iEventRepo.findById(eventId)
        .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        EventMemberType eventMemberType = iEventMemberType.findById(eventFeesDto.getId()).orElseThrow(
                () -> new EntityNotFoundException("Member Type Not Found")
        );
        eventMemberType.setEntryFees(eventFeesDto.getEntryFees());
        iEventMemberType.save(eventMemberType);
            return "Fees update successfully";
    }

    private EventMemberTypeResponse createResponse(EventMemberType eventMemberType)
    {
        return new EventMemberTypeResponse(
                eventMemberType.getId(),
                eventMemberType.getMemberTypeName(),
                eventMemberType.getEntryFees()
        );
    }
}
