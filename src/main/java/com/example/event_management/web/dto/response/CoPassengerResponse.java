package com.example.event_management.web.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoPassengerResponse {

    private String name;
    private Integer age;
    private String relationship;
}
