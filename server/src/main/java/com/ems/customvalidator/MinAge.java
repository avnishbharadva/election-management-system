package com.ems.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


/**
 * Custom validation annotation to enforce a minimum age constraint.
 * This annotation can be applied to fields and is validated using {@code AgeValidator}.
 */
@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinAge {
    /**
     * Specifies the minimum required age for validation.
     *
     * @return the minimum age value
     */
    int value();

    String message() default "Age must be at least {value} years";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
