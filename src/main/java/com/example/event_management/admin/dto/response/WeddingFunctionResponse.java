package com.example.event_management.admin.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class WeddingFunctionResponse {

    private Long functionId;
    private String weddingId;
    private Integer sideId;
    private String sideName;

    private String functionName;
    private LocalDate functionDate;
    private LocalTime functionStartTime;
    private LocalTime functionEndTime;

    private String venueName;
    private String venueAddress;
    private String city;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
