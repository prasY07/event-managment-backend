package com.example.event_management.app.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    
    
    // private String memberTypeName;
    private UserPersonalInfoResponse userPersonalInfoResponse; 
    private EventResponse event;
    private List<EventAccessResponse> accessList;

}
