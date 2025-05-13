package com.example.event_managment.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EventMemberAccessResponse {
    private Long memberId;
    private Long eventId;
    private List<Integer> access;
}
