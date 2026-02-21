package com.example.event_management.admin.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateEmployeeDto {

    private Long id; // Optional — for update

    @NotBlank(message = "Employee name is required.")
    @Size(min = 2, max = 100, message = "Employee name must be between 2 and 100 characters.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Please provide a valid email address.")
    private String email;

    @NotNull(message = "Country is required.")
    private Long countryId;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Phone number must contain only digits and be 7–15 digits long.")
    private String phoneNumber;

}
