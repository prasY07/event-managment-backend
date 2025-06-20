package com.example.event_management.commonapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CountryResponse {
    private Long id;
    private String name;
    private String countryCode;
}
