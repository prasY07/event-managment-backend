package com.example.event_managment.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.event_managment.admin.dto.EventRegistrationWithoutQRDto;
import com.example.event_managment.admin.dto.response.EventRegistrationWithoutQRResponse;
import com.example.event_managment.common.helpers.admin.EventHelper;
import com.example.event_managment.common.service.QRCodeCreationEmailSendService;
import com.example.event_managment.entity.Event;
import com.example.event_managment.entity.EventMemberType;
import com.example.event_managment.entity.EventRegistration;
import com.example.event_managment.entity.SocialMediaSource;
import com.example.event_managment.entity.State;
import com.example.event_managment.repository.IEventMemberTypeRepo;
import com.example.event_managment.repository.IEventRegistrationRepo;
import com.example.event_managment.repository.IEventRepo;
import com.example.event_managment.repository.ISocialMediaSourceRepo;
import com.example.event_managment.repository.IStateRepo;

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

        // @Autowired
        // private EventHelper eventHelper;

        @Autowired
        private QRCodeCreationEmailSendService qrCodeCreationEmailSendService;

        public EventRegistrationWithoutQRResponse newRegistration(EventRegistrationWithoutQRDto dto) {

                Event event = iEventRepo.findById(dto.getEventId())
                                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

                SocialMediaSource heardSource = iSocialMediaSourceRepo.findById(dto.getHeardSourceId())
                                .orElseThrow(() -> new EntityNotFoundException("Heard Source not found"));

                State state = iStateRepo.findById(dto.getStateId())
                                .orElseThrow(() -> new EntityNotFoundException("State not found"));

                // EventMemberType memberType =
                // iEventMemberTypeRepo.findById(dto.getMemberTypeId())
                // .orElseThrow(() -> new EntityNotFoundException("Member Type not found"));

                EventMemberType memberType = iEventMemberTypeRepo
                                .findByIdAndEventId(dto.getMemberTypeId(), dto.getEventId())
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
                registration.setState(state);
                registration.setEvent(event);
                registration.setAddress(dto.getAddress());
                registration.setZipcode(dto.getZipcode());
                registration.setAddedBy(dto.getAddedBy());
                registration.setRegistrationId(registrationId);

                // Save the entity
                EventRegistration saved = iEventRegistrationRepo.save(registration);

                qrCodeCreationEmailSendService.processRegistration(
                                dto.getEventId(),
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

}
