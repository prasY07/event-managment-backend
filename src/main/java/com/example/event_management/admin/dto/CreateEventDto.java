package com.example.event_management.admin.dto;

import java.time.LocalDate;

import com.example.event_management.common.AppStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventDto {


    private String title;
    private String address;
    private String venue;
    private String category;
    private String description;
    private String privacyPolicy;
    private String sponsoredBy;
    private AppStatus.EventFoc isFoc;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationEndDate;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationStartDate;

    private Long userId;

    private String eventStartTime;
    private String eventEndTime;

    private String eventMemberType;

    private String eventAccessType;

    private String eventServices;


   
}
