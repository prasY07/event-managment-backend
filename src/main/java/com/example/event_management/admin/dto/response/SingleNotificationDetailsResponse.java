package com.example.event_management.admin.dto.response;

import com.example.event_management.common.AppStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class SingleNotificationDetailsResponse {

    private Long id;
    private String title;
    private String message;
    private LocalDate date;
    private LocalTime time;
    private AppStatus.notificationStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long weddingId;

    private Long weddingFunctionId;

}
