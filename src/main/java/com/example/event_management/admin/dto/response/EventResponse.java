package com.example.event_management.admin.dto.response;

import java.time.LocalDate;

import com.example.event_management.common.AppStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventResponse {
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate registrationEndDate;
    private String venue;
    private String address;
    private String category;
    private String description;
    private String privacyPolicy;
    private String eventId;
    private String image;
    private AppStatus.EventStatus eventStatus;
    private AppStatus.EStatus status;
    private UserShortResponse user;
}
