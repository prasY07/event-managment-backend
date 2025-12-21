package com.example.event_management.web.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GuestListResponse {

    private Long guestId;
    private Long weddingId;
    private String fullName;
    private String mobileNumber;
    private String email;
    private String gender;

    private JourneyResponse onwardJourney;
    private JourneyResponse returnJourney;

    private List<CoPassengerResponse> coPassengers;
}
