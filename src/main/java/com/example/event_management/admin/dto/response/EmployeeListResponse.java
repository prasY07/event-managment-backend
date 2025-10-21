package com.example.event_management.admin.dto.response;

import com.example.event_management.common.AppStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeListResponse {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String countryCode;
    private AppStatus.EmployeeStatus status;
    
}
