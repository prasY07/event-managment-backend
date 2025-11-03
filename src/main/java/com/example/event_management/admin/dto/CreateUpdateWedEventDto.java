package com.example.event_management.admin.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CreateUpdateWedEventDto {

    private String groomName;
    private String brideName;

    private String groomFatherName;
    private String groomMotherName;

    private String brideFatherName;
    private String brideMotherName;
    private String coupleName;

    private LocalDate weddingDate;
    private LocalDate registrationStartDate;
    private LocalDate registrationEndDate;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    
}
