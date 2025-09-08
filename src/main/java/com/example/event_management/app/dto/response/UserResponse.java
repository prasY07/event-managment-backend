package com.example.event_management.app.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    
    private String name;
    private String email;
    private String mobileNumber;
    private String memberTypeName;
    private EventResponse event;
    // private List<EventAccessResponse> accessList;

}
