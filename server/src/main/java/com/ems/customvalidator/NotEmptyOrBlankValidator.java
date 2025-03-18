package com.ems.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyOrBlankValidator implements ConstraintValidator<NotEmptyOrBlank, String> {

    private String nullMessage;
    private String emptyMessage;
    private String blankMessage;

    @Override
    public void initialize(NotEmptyOrBlank constraintAnnotation) {
        this.nullMessage = constraintAnnotation.nullMessage();
        this.emptyMessage = constraintAnnotation.emptyMessage();
        this.blankMessage = constraintAnnotation.blankMessage();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {  // Null check
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(nullMessage)
                    .addConstraintViolation();
            return false;
        }

        if (value.isEmpty()) {  // Empty string check
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(emptyMessage)
                    .addConstraintViolation();
            return false;
        }

        if (value.trim().isEmpty()) {  // Blank string check (only spaces)
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(blankMessage)
                    .addConstraintViolation();
            return false;
        }

        return true; // Valid input
    }
}
