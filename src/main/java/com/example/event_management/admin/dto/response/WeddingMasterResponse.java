package com.example.event_management.admin.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class WeddingMasterResponse {
    private Long id;
    private String groomName;
    private String brideName;
    private String coupleName;
    private LocalDate weddingDate;
    private LocalDate regEndDate;
    private LocalDate redStartDate;
}
