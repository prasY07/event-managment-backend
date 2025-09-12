package com.example.event_management.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignEventServiceDayDto {
    private Long eventId;
    private Long serviceId;
    private List<Long> memberTypeId;
    private Long dayId;


}
