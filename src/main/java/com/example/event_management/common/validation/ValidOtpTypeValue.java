package com.example.event_management.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OtpTypeValueValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOtpTypeValue {
    String message() default "Invalid phone/email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}