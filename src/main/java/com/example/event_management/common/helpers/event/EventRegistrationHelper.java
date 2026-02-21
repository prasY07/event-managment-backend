package com.example.event_management.common.helpers.event;


import com.example.event_management.admin.dto.EventRegistrationWithoutQRDto;
import com.example.event_management.admin.dto.response.EventRegisterUserShortResponse;
import com.example.event_management.admin.dto.response.EventRegistrationWithoutQRResponse;
import com.example.event_management.common.AppStatus;
import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.common.service.QRCodeCreationEmailSendService;
import com.example.event_management.entity.*;
import com.example.event_management.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EventRegistrationHelper {

    private final IEventRegistrationRepo iEventRegistrationRepo;

    private final IEventRepo iEventRepo;

    private final ISocialMediaSourceRepo iSocialMediaSourceRepo;

    private final IStateRepo iStateRepo;

    private final IEventMemberTypeRepo iEventMemberTypeRepo;

    private final ICountryRepo iCountryRepo;
    private final QRCodeCreationEmailSendService qrCodeCreationEmailSendService;
    private final EventOtpHelper eventOtpHelper;

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

        EventMemberType memberType = iEventMemberTypeRepo
                .findByIdAndEventId(dto.getMemberTypeId(), event.getId())
                .orElseThrow(() -> new EntityNotFoundException("Member Type not found for this event"));

        EventRegistration registration = new EventRegistration();

        String registrationId;

        do {
            registrationId = EventHelper.createUserUniqueRegistrationID();
        } while (iEventRegistrationRepo.existsByRegistrationId(registrationId));

//        ResponseEntity<ApiResponse<String>> verifyOtpResponse = eventOtpHelper.verifyOtp(dto.getUserOtpId(), dto.getOtp());
//        if (verifyOtpResponse.getBody() != null && verifyOtpResponse.getBody().isSuccess()) {

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
            registration.setAddedBy(dto.getEventRegistrationAddedBy());
            registration.setRegistrationId(registrationId);

            // Save the entity
            EventRegistration saved = iEventRegistrationRepo.save(registration);

            qrCodeCreationEmailSendService.processRegistration(
                    event.getId(),
                    registrationId,
                    dto.getEmail());

            // Convert to response
            return createResponse(saved);
//        } else {
//            throw new RuntimeException(verifyOtpResponse.getBody().getMessage());
//
//        }
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
