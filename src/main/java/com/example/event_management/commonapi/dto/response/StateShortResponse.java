package com.example.event_management.commonapi.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class StateShortResponse {
    private Long id;
    private String name;
}
