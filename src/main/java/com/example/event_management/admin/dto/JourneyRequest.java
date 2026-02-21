package com.example.event_management.admin.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class JourneyRequest {
    private String mode;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private String departureCity;
    private String destinationCity;
    private String carrierName;
    private String carrierNumber;
    private List<CoPassengerRequest> coPassengers;
}
