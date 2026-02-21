package com.example.event_management.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.event_management.admin.dto.EventRegistrationWithoutQRDto;
import com.example.event_management.admin.dto.response.EventRegisterUserShortResponse;
import com.example.event_management.admin.dto.response.EventRegistrationWithoutQRResponse;
import com.example.event_management.common.AppStatus;
import com.example.event_management.common.helpers.event.EventHelper;
import com.example.event_management.common.response.PaginationResponse;
import com.example.event_management.common.service.QRCodeCreationEmailSendService;
import com.example.event_management.entity.Country;
import com.example.event_management.entity.Event;
import com.example.event_management.entity.EventMemberType;
import com.example.event_management.entity.EventRegistration;
import com.example.event_management.entity.SocialMediaSource;
import com.example.event_management.repository.ICountryRepo;
import com.example.event_management.repository.IEventMemberTypeRepo;
import com.example.event_management.repository.IEventRegistrationRepo;
import com.example.event_management.repository.IEventRepo;
import com.example.event_management.repository.ISocialMediaSourceRepo;
import com.example.event_management.repository.IStateRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventRegistrationService {

        @Autowired
        IEventRegistrationRepo iEventRegistrationRepo;

        @Autowired
        IEventRepo iEventRepo;

        @Autowired
        ISocialMediaSourceRepo iSocialMediaSourceRepo;

        @Autowired
        IStateRepo iStateRepo;

        @Autowired
        IEventMemberTypeRepo iEventMemberTypeRepo;

        @Autowired
        ICountryRepo iCountryRepo;

        @Autowired
        private QRCodeCreationEmailSendService qrCodeCreationEmailSendService;

        
    public PaginationResponse<List<EventRegisterUserShortResponse>> getAllEventUser( Long eventId,int page, int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        Page<EventRegistration> eventRegisterPage = iEventRegistrationRepo.findByEventId(eventId,pageable);

        List<EventRegisterUserShortResponse> registerUser = eventRegisterPage.getContent()
                .stream()
                .map(this::createShortResponse)
                .toList();

        return new PaginationResponse<>(
                registerUser,
                page,
                size,
                eventRegisterPage.getTotalElements(),
                eventRegisterPage.getTotalPages());
    }


        public EventRegistrationWithoutQRResponse newRegistration(EventRegistrationWithoutQRDto dto) {

            Event event = iEventRepo.findEventByUUID(dto.getEventId());
            if(event == null)
            {
                throw new EntityNotFoundException("Event Not found");
            }

                SocialMediaSource heardSource = iSocialMediaSourceRepo.findById(dto.getHeardSourceId())
                                .orElseThrow(() -> new EntityNotFoundException("Heard Source not found"));

                Country country = iCountryRepo.findById(dto.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid country selected"));

                    Map<String, Object> response = EventHelper.eventRegistrationCases(event);

                if (!(Boolean) response.get("status")) {
                    throw new IllegalArgumentException(response.get("msg").toString());
                }

                // if(event.getStatus() != AppStatus.EStatus.ACTIVE) {
                //         throw new IllegalArgumentException("Event is not active. you can not register for this event");
                // }
                // if (LocalDate.now().isAfter(event.getRegistrationEndDate())) {
                //          throw new IllegalArgumentException("Registration has been closed for this event");
                // }
                // State state = iStateRepo.findById(dto.getStateId())
                //                 .orElseThrow(() -> new EntityNotFoundException("State not found"));

                // EventMemberType memberType =
                // iEventMemberTypeRepo.findById(dto.getMemberTypeId())
                // .orElseThrow(() -> new EntityNotFoundException("Member Type not found"));

                EventMemberType memberType = iEventMemberTypeRepo
                                .findByIdAndEventId(dto.getMemberTypeId(), event.getId())
                                .orElseThrow(() -> new EntityNotFoundException("Member Type not found for this event"));

                EventRegistration registration = new EventRegistration();

                String registrationId;

                do {
                        registrationId = EventHelper.createUserUniqueRegistrationID();
                } while (iEventRegistrationRepo.existsByRegistrationId(registrationId));

                registration.setName(dto.getName());
                registration.setEmail(dto.getEmail());
                registration.setMemberTypeId(memberType);
                registration.setGender(dto.getGender());
                registration.setHeardSourceId(heardSource);
                registration.setEvent(event);
                registration.setAddress(dto.getAddress());
                registration.setZipcode(dto.getZipcode());
                registration.setPhoneNumber(dto.getPhoneNumber());
                registration.setCountry(country);
                registration.setRegistrationId(registrationId);
                registration.setAddedBy(AppStatus.EventRegistrationAddedBy.ADMIN);
                registration.setRegistrationId(registrationId);

                // Save the entity
                EventRegistration saved = iEventRegistrationRepo.save(registration);

                qrCodeCreationEmailSendService.processRegistration(
                                event.getId(),
                                registrationId,
                                dto.getEmail());

                // Convert to response
                return createResponse(saved);
        }

        private EventRegistrationWithoutQRResponse createResponse(EventRegistration eventRegistration) {
                return new EventRegistrationWithoutQRResponse(
                                eventRegistration.getId(),
                                eventRegistration.getName(),
                                eventRegistration.getEmail(),
                                eventRegistration.getMemberTypeId() != null
                                                ? eventRegistration.getMemberTypeId().getId()
                                                : null,
                                eventRegistration.getGender(),
                                eventRegistration.getHeardSourceId() != null
                                                ? eventRegistration.getHeardSourceId().getId()
                                                : null,
                                eventRegistration.getState() != null ? eventRegistration.getState().getId() : null,
                                eventRegistration.getEvent() != null ? eventRegistration.getEvent().getId() : null,
                                eventRegistration.getAddress(),
                                eventRegistration.getZipcode(),
                                eventRegistration.getCreatedAt(),
                                eventRegistration.getUpdatedAt(),
                                eventRegistration.getAddedBy());
        }

         private EventRegisterUserShortResponse createShortResponse(EventRegistration eventRegistration) {
                return new EventRegisterUserShortResponse(
                                eventRegistration.getId(),
                                eventRegistration.getName(),
                                eventRegistration.getEmail(),
                                eventRegistration.getCreatedAt().toLocalDate());
        }

}
