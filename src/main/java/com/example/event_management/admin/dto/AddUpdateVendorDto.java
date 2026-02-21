package com.example.event_management.admin.dto;

import lombok.Getter;

@Getter
public class AddUpdateVendorDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Long countryId;
    private Long stateId;
    private Long vendorTypeId;
}
