package com.example.event_management.admin.dto;

import lombok.Data;

@Data
public class GuestRequest {
    private Long weddingId;
    private String fullName;
    private String mobileNumber;
    private String email;
    private String gender;
    private JourneyRequest onwardJourney;
    private JourneyRequest returnJourney;
}
