package com.example.event_management.app.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventShortResponse {
    private Long id;
    private String title;
}
