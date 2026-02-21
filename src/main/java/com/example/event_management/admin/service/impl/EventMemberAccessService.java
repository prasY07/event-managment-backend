package com.example.event_management.admin.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.event_management.admin.dto.EventMemberAccessDto;
import com.example.event_management.admin.dto.response.EventMemberAccessResponse;
import com.example.event_management.entity.Event;
import com.example.event_management.entity.EventAccessType;
import com.example.event_management.entity.EventMemberAccess;
import com.example.event_management.entity.EventMemberType;
import com.example.event_management.repository.IEventAccessTypeRepo;
import com.example.event_management.repository.IEventMemberAccessRepo;
import com.example.event_management.repository.IEventMemberTypeRepo;
import com.example.event_management.repository.IEventRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventMemberAccessService {

    @Autowired
    IEventRepo iEventRepo;

    @Autowired
    IEventMemberTypeRepo iEventMemberType;

    @Autowired
    IEventMemberAccessRepo iEventMemberAccess;

    @Autowired
    IEventAccessTypeRepo iEventAccessType;

    public String createEventMemberAccess(EventMemberAccessDto eventMemberAccessDto) {
        Event event = iEventRepo.findById(eventMemberAccessDto.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        EventMemberType eventMemberType = iEventMemberType.findById(eventMemberAccessDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("Member Type not found"));

        try {
            iEventMemberAccess.deleteData(eventMemberAccessDto.getEventId(), eventMemberAccessDto.getMemberId());
        } catch (DataAccessException ex) {
            throw new RuntimeException("Failed to delete existing event member access records. Please try again.", ex);
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred while deleting event member access.", ex);
        }

        for (Long accessTypeId : eventMemberAccessDto.getAccessId()) {
            EventAccessType accessType = iEventAccessType.findById(accessTypeId)
                    .orElseThrow(() -> new EntityNotFoundException("Access Type not found"));
            EventMemberAccess eventMemberAccess = new EventMemberAccess();
            eventMemberAccess.setEventId(event);
            eventMemberAccess.setMemberTypeId(eventMemberType);
            eventMemberAccess.setEventAccessTypeId(accessType);
            iEventMemberAccess.save(eventMemberAccess);
        }

        return "Access Provided";

    }

    public List<EventMemberAccessResponse> getAllMemberAccessList(Long eventId) {
        // Event event = iEventRepo.findById(eventId)
        // .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        iEventRepo.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));
        List<Object[]> results = iEventMemberAccess.findAccessTypesByEventId(eventId);

        List<EventMemberAccessResponse> responseList = new ArrayList<>();

        for (Object[] row : results) {
            Long memberTypeId = ((Number) row[0]).longValue(); // member_type_id
            Long eventIdFromRow = ((Number) row[1]).longValue(); // event_id
            String accessIdsStr = (String) row[2]; // comma-separated access IDs

            List<Integer> accessIds = Arrays.stream(accessIdsStr.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            responseList.add(new EventMemberAccessResponse(memberTypeId, eventIdFromRow, accessIds));
        }

        return responseList;
    }

    // private EventMemberAccessResponse createResponse(EventMemberAccess
    // eventMemberAccess)
    // {
    //
    // List<Long> accessIds = new ArrayList<>();
    // accessIds.add(eventMemberAccess.getEventAccessType().getId()); // assuming ID
    // is Integer
    //
    // return new EventMemberAccessResponse(
    // eventMemberAccess.getMemberTypeId().getId(), // unwrap entity to ID
    // eventMemberAccess.getEventId().getId(), // unwrap entity to ID
    // accessIds
    // );
    // }
}
