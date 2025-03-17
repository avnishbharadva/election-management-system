package com.ems.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = AgeValidator.class)  // Links to our custom validator logic
@Target({ElementType.FIELD})  // Can be applied to fields
@Retention(RetentionPolicy.RUNTIME)
public @interface MinAge {
    int value();  // Minimum age

    String message() default "Age must be at least {value} years";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
