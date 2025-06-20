package com.example.event_management.admin.dto;


import java.time.LocalDateTime;

import com.example.event_management.common.AppStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRegistrationDto {
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
