package com.ems.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotEmptyOrBlankValidator.class) // Links to custom validator
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface NotEmptyOrBlank {

    String message() default "Invalid input"; // Default message (not used directly)

    String nullMessage() default "Field cannot be null"; // Message for null check

    String emptyMessage() default "Field cannot be empty"; // Message for empty check

    String blankMessage() default "Field cannot be blank"; // Message for blank check

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
