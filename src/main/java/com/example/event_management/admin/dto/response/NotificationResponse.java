package com.example.event_management.admin.dto.response;

import com.example.event_management.common.AppStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private String title;
    private String message;
    private LocalDate date;
    private LocalTime time;
    private AppStatus.notificationStatus status;

}
