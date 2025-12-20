package com.example.event_management.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {

    private Long weddingId;
    private Long weddingFunctionId;
    private String title;
    private String message;
    private LocalDate notificationDate;
    private LocalTime notificationTime;
}
