package com.ems.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.*;


/**
 * Custom validation annotation to ensure a field is neither null, empty, nor blank.
 * This annotation is validated using {@code NotEmptyOrBlankValidator}.
 */
@Documented
@Constraint(validatedBy = NotEmptyOrBlankValidator.class) // Links to custom validator
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface NotEmptyOrBlank {

    String message() default "Invalid input";

    String nullMessage() default "Field cannot be null";

    String emptyMessage() default "Field cannot be empty";

    String blankMessage() default "Field cannot be blank";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
