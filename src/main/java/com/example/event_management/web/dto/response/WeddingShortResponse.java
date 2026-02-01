package com.example.event_management.web.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeddingShortResponse {

    private LocalDate startDate;
    private LocalDate endDate;
    private String cardUrl;
}
