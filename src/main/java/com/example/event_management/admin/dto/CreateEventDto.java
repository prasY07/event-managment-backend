package com.example.event_management.admin.dto;

import java.time.LocalDate;
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


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationEndDate;
    private Long userId;

    private String eventStartTime;
    private String eventEndTime;

    private String eventMemberType;

    private String eventAccessType;

    private String eventServices;


   
}
