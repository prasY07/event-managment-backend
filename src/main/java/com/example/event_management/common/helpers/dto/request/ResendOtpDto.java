package com.example.event_management.common.helpers.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResendOtpDto {

    @NotNull(message = "User OTP ID cannot be null")
    private String userOtpId;
}
