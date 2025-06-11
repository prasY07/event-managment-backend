package com.example.event_managment.admin.dto;

import com.example.event_managment.common.AppStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStatusDto {
    private AppStatus.UserStatus status;
}
