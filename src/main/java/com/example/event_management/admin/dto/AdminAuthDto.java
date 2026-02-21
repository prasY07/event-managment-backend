package com.example.event_management.admin.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminAuthDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format") // automatically checks pattern
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
    
}
