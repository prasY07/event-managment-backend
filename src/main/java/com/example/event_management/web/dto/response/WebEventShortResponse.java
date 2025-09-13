package com.example.event_management.web.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebEventShortResponse {
    private Long id;
    private String title;
    private String description;
    private String privacyPolicy;
    private LocalDate startDate ;
    private LocalDate endDate;
}
