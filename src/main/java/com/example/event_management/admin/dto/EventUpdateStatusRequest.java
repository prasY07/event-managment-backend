package com.example.event_management.admin.dto;

import com.example.event_management.common.AppStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventUpdateStatusRequest {
    private AppStatus.EventStatus eventStatus;

}
