package com.example.event_management.admin.dto.response;

import com.example.event_management.common.AppStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class EventRegistrationWithoutQRResponse {

   private Long id;

    private String name;

    private String email;

    private Long memberTypeId; // Just ID for DTO to keep it simple

    private String gender;

    private Long heardSourceId;

    private Long stateId;

    private Long eventId;

    private String address;

    private String zipcode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private AppStatus.EventRegistrationAddedBy addedBy = AppStatus.EventRegistrationAddedBy.SELF;
}
