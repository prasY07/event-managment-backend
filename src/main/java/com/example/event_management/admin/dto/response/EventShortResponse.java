package com.example.event_management.admin.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventShortResponse {
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate lastRegDate;
    private LocalTime eventStartTime;
    private LocalTime eventEndTime;
    private String venue;
    private String address;
    private String category;
    private String description;
    private String privacyPolicy;
     private UserShortResponse user;

}
