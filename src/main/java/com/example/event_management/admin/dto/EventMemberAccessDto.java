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
public class EventMemberAccessDto {
    private Long id;
    private Long eventId;
    private Long memberId;
    private List<Long> accessId;


}
