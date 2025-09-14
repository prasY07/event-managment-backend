package com.example.event_management.web.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebEventShortResponse {
//    private Long id;
    private String title;
    private String description;
    private String privacyPolicy;
    private LocalDate startDate ;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String sponsoredBy;

}
