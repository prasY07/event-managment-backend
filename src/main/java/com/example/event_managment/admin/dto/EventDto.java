package com.example.event_managment.admin.dto;

import java.time.LocalDate;

import com.example.event_managment.common.AppStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    private Long id;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationEndDate;

    private String venue;
    private String address;

    private String category;
    private String description;

    private String privacyPolicy;

    private String eventId;

    private AppStatus.EStatus status;
    private AppStatus.EventStatus eventStatus;

    private Long userId;

    private String eventMemberType;

    private String eventAccessType;

    // Custom toString method to print the content of EventDto in a readable format
    @Override
    public String toString() {
        return "EventDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", venue='" + venue + '\'' +
                ", address='" + address + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", userId=" + userId +
                '}';
    }
}
