package com.example.event_management.admin.dto;

import com.example.event_management.common.AppStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUpdateUserDto {
    private String name;
    private String email;
    private String phoneNumber;
    private Long countryId;
    private AppStatus.UserRole role;
}
