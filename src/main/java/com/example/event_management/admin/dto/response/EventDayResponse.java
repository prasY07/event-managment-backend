package com.example.event_management.admin.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventDayResponse {
    private Long id;
    private LocalDate date;
}
