package com.example.event_management.common.validation;

import com.example.event_management.common.helpers.dto.request.SendOtpDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;


public class OtpTypeValueValidator implements ConstraintValidator<ValidOtpTypeValue, SendOtpDto> {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    @Override
    public boolean isValid(SendOtpDto dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getOtpType() == null || dto.getTypeValue() == null) {
            return false;
        }

        boolean valid = true;
        String errorMessage = null;

        switch (dto.getOtpType()) {
//            case PHONE:
//                valid = PHONE_PATTERN.matcher(dto.getTypeValue()).matches();
//                if (!valid) errorMessage = "Invalid phone number. Must be 10 digits.";
//                break;
            case EMAIL:
                valid = EMAIL_PATTERN.matcher(dto.getTypeValue()).matches();
                if (!valid) errorMessage = "Invalid email address format.";
                break;
        }

        if (!valid && errorMessage != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage)
                    .addPropertyNode("typeValue")
                    .addConstraintViolation();
        }

        return valid;
    }
}
