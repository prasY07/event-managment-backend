package com.example.event_management.app.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.event_management.app.dto.response.EventAccessResponse;
import com.example.event_management.app.dto.response.EventDayServiceListResponse;
import com.example.event_management.app.dto.response.EventResponse;
import com.example.event_management.app.dto.response.EventShortResponse;
import com.example.event_management.app.dto.response.UserPersonalInfoResponse;
import com.example.event_management.app.dto.response.UserResponse;
import com.example.event_management.common.AppStatus;
import com.example.event_management.common.exception.DataNotFoundException;
import com.example.event_management.entity.Event;
import com.example.event_management.entity.EventAccessType;
import com.example.event_management.entity.EventDay;
import com.example.event_management.entity.EventMemberType;
import com.example.event_management.entity.EventRegistration;
import com.example.event_management.entity.EventService;
import com.example.event_management.repository.IEventAccessTypeRepo;
import com.example.event_management.repository.IEventDayRepo;
import com.example.event_management.repository.IEventMemberAccessRepo;
import com.example.event_management.repository.IEventMemberTypeRepo;
import com.example.event_management.repository.IEventRegistrationRepo;
import com.example.event_management.repository.IEventRepo;
import com.example.event_management.repository.IEventServiceRepo;
import com.example.event_management.repository.IMemberTypeServiceAccessRepo;
import com.example.event_management.repository.IUserServiceUsageRepo;

import jakarta.persistence.EntityNotFoundException;

@Service("appEventService")
public class AppEventService {

    @Autowired
    IEventRegistrationRepo iEventRegistrationRepo;

    @Autowired
    IEventRepo iEventRepo;

    @Autowired
    IEventMemberAccessRepo iEventMemberAccessRepo;

    @Autowired
    IEventAccessTypeRepo iEventAccessTypeRepo;

    @Autowired
    IEventDayRepo iEventDayRepo;

    @Autowired
    IEventMemberTypeRepo iEventMemberTypeRepo;

    @Autowired
    IEventServiceRepo IEventServiceRepo;

    @Autowired
    IMemberTypeServiceAccessRepo iMemberTypeServiceAccessRepo;

    @Autowired
    IUserServiceUsageRepo iUserServiceUsageRepo ;



    public UserResponse getUserInformation(String regId) {
        EventRegistration userInfo = iEventRegistrationRepo.findByRegistrationId(regId);
        if (userInfo == null) {
            throw new EntityNotFoundException("User not found");
        }

        Event event = iEventRepo.findById(userInfo.getEvent().getId()).
                orElseThrow(() -> new EntityNotFoundException("Event not found"));

        return createResponse(userInfo);

    }

    public List<EventShortResponse> getCurrentActiveEvent()
    {
        LocalDate todayDate = LocalDate.now();
        System.out.println("todayDate"+todayDate);
        System.out.println("AppStatus"+AppStatus.EStatus.ACTIVE);
        List<Event> res = iEventRepo.getTodayActiveEvent(todayDate, AppStatus.EStatus.ACTIVE.name());
        // if(res.isEmpty())
        // {
        //     return Collections.emptyList(); 
        // }
        List<EventShortResponse> response = res.stream()
                .map(this::createEventShortResponse) // convert entity -> response
                .toList();

                return response;
        
    }



    public List<EventDayServiceListResponse> getEventDayService(LocalDate date, Long MemberTypeId, Long eventId) {
        Event event = iEventRepo.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found=="));
        EventDay eventDay = iEventDayRepo.checkDayExists(date, eventId);
        if (eventDay == null) {
            throw new DataNotFoundException("Day Not Found");
        }

        EventMemberType member = iEventMemberTypeRepo.findById(MemberTypeId).orElseThrow(() -> new EntityNotFoundException("member not found"));

        List<Long> allServiceList = iMemberTypeServiceAccessRepo.getAllServiceId(event.getId(), eventDay.getDayId(), member.getId());
        if (allServiceList == null || allServiceList.isEmpty()) {
            return Collections.emptyList();
        }

        List<EventService> getAllServices = IEventServiceRepo.getAllEventServices(allServiceList);

        List<EventDayServiceListResponse> serviceList = getAllServices
                .stream()
                .map(
                    service ->
                    {
                Boolean used = iUserServiceUsageRepo.hasUserUsedService(event.getId(), service.getServiceId());
                return new EventDayServiceListResponse(
                        service.getServiceId(),
                        service.getServiceName(),
                        used
                );
            }
                )
                .toList();

        return serviceList;
    }

    private UserResponse createResponse(EventRegistration userInfo) {

        Event event = userInfo.getEvent();
        EventResponse eventResponse = new EventResponse(
                event.getTitle(),
                userInfo.getMemberTypeId().getMemberTypeName()
        );

        UserPersonalInfoResponse userPersonalInfo = new UserPersonalInfoResponse(
                userInfo.getName(),
                userInfo.getEmail(),
                userInfo.getPhoneNumber()
        );
        List<Long> res = iEventMemberAccessRepo.getAllAccessId(event.getId(), userInfo.getMemberTypeId().getId());

        List<EventAccessType> eventAccessList = iEventAccessTypeRepo.getAllAccessList(res);

        List<EventAccessResponse> accessList = eventAccessList
                .stream()
                .map(this::createEventAccessResponse) // convert entity -> response
                .toList();

        return new UserResponse(
                userPersonalInfo,
                eventResponse,
                accessList
        );
    }

    private EventAccessResponse createEventAccessResponse(EventAccessType entity) {
        return new EventAccessResponse(
                entity.getId(),
                entity.getEventAccessTypeName()
        );
    }

   private EventShortResponse createEventShortResponse(Event event) {
       return new EventShortResponse(
               event.getId(),
               event.getTitle()
       );
   }
}
