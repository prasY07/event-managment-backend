package com.example.event_management.common.helpers.dto.request;

import com.example.event_management.common.AppStatus;
import com.example.event_management.common.validation.ValidOtpTypeValue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ValidOtpTypeValue
public class SendOtpDto {

    @NotNull(message = "OTP type is required")
    private AppStatus.OtpType otpType;
    @NotNull(message = "Type value is required")
    private String typeValue;
    private String role;

}
