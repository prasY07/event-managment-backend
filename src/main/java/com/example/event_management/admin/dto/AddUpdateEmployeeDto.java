package com.example.event_management.admin.dto;


import lombok.Getter;

@Getter
public class AddUpdateEmployeeDto {
      private String name;
    private String email;
    private String phoneNumber;
    private Long countryId;
}
