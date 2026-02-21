package com.example.event_management.web.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JourneyResponse {

    private String mode;
    private String arrivalDate;
    private String arrivalTime;
    private String departureDate;
    private String departureTime;
    private String departureCity;
    private String destinationCity;
    private String carrierName;
    private String carrierNumber;
    private String ticketPath;
}
