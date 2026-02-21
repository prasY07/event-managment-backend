package com.example.event_management.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventDayServiceListResponse {
    private Long id;
    private String name;
    private Boolean isServiceUse;
}
