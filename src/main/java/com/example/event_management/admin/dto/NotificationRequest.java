package com.example.event_management.admin.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class NotificationRequest {

    private Long weddingId;
    private Long weddingFunctionId;
    private String title;
    private String message;
    private LocalDate notificationDate;
    private LocalTime notificationTime;
}
