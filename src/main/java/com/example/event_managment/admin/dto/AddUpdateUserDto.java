package com.example.event_managment.admin.dto;

import com.example.event_managment.common.AppStatus;

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
