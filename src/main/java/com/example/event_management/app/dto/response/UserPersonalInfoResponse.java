package com.example.event_management.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserPersonalInfoResponse {
    
    private String name;
    private String email;
    private String mobileNumber;

}
