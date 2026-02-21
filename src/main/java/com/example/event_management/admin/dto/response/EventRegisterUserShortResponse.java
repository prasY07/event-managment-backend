package com.example.event_management.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class EventRegisterUserShortResponse {
    private Long id;
    private String name;
    private String email;
    private LocalDate createdAt;
   
}
