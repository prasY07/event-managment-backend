package com.example.event_managment.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class EventMemberTypeResponse {
    private Long id;
    private String name;
    private BigDecimal entryFees;
}
