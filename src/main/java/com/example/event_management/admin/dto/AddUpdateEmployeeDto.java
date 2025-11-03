package com.example.event_management.admin.dto;


import com.example.event_management.common.AppStatus;

import lombok.Getter;

@Getter
public class AddUpdateEmployeeDto {
    private String name;
    private String email;
    private String phoneNumber;
    private AppStatus.ETYPE eType;
    private Long countryId;
}
