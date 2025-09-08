package com.example.event_management.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.event_management.admin.dto.response.UserShortResponse;
import com.example.event_management.app.dto.response.EventAccessResponse;
import com.example.event_management.app.dto.response.EventResponse;
import com.example.event_management.app.dto.response.UserResponse;
import com.example.event_management.common.helpers.UrlHelper;
import com.example.event_management.entity.Event;
import com.example.event_management.entity.EventRegistration;
import com.example.event_management.entity.MemberTypeServiceAccess;
import com.example.event_management.entity.User;
import com.example.event_management.repository.IEventMemberAccessRepo;
import com.example.event_management.repository.IEventRegistrationRepo;
import com.example.event_management.repository.IEventRepo;

import jakarta.persistence.EntityNotFoundException;

@Service("appEventService")
public class EventService {
    
    @Autowired
    IEventRegistrationRepo iEventRegistrationRepo;

    @Autowired
    IEventRepo iEventRepo;

    @Autowired
    IEventMemberAccessRepo iEventMemberAccessRepo;

    //  public void getUserInformation(String regId)
    // {
      

    // }

    public UserResponse getUserInformation(String regId)
    {
       EventRegistration userInfo =   iEventRegistrationRepo.findByRegistrationId(regId);
        if(userInfo == null)
        {
            throw new EntityNotFoundException("User not found");
        }

        Event event = iEventRepo.findById(userInfo.getEvent().getId()).
                orElseThrow(() -> new EntityNotFoundException("Event not found"));

        return createResponse(userInfo);

    }

     private UserResponse createResponse(EventRegistration userInfo) {
       
        Event event = userInfo.getEvent();
        EventResponse eventResponse = new EventResponse(
            event.getTitle()
        );

        

        // List<EventAccessResponse> accessList = iEventMemberAccessRepo
        //     .eventMemberType(userInfo.getMemberTypeId())
        //     .stream()
        //     .map(this::createEventAccessResponse)   // convert entity -> response
        //     .toList();

        return new UserResponse(
        userInfo.getName(),
        userInfo.getEmail(),
        userInfo.getPhoneNumber(),
        userInfo.getMemberTypeId().getMemberTypeName(),
        eventResponse
        // accessList
    );
    }

//   private EventAccessResponse createEventAccessResponse(MemberTypeServiceAccess entity) {
//     return new EventAccessResponse(
//         entity.getAccessName(),
//         entity.getDescription()
//     );
// }
}
