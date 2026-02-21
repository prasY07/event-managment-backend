package com.example.event_management.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessDetailsDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String fname;

    @NotBlank
    @Size(min = 2, max = 100)
    private String lname;

    @NotBlank
    private String gender; // "Male", "Female", "Other"

    @NotBlank
    @Size(min = 10, max = 15)
    private String phoneNumber;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 150)
    private String companyName;

    @NotBlank
    @Size(max = 255)
    private String companyAddress;

    private String businessSummary;

    @NotBlank
    private String eemaMember; // "Yes", "No", "Pending"



    @NotBlank
    private String emaa; // "Yes", "No", "Pending"

    @NotBlank
    private String categories; // comma-separated string
}
