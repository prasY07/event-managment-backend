package com.example.event_management.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class WeddingFunctionRequest {

    @NotNull
    private Long weddingId;

    @NotNull
    private Integer sideId;

    @NotBlank
    private String functionName;

    @NotNull
    private LocalDate functionDate;

    private LocalTime functionStartTime;
    private LocalTime functionEndTime;

    private String venueName;
    private String venueAddress;
    private String city;
}
