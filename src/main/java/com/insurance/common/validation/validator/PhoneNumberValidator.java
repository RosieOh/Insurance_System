package com.core.common.validation.validator;

import java.util.regex.Pattern;

import com.core.common.validation.annotation.PhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    
    private static final String PHONE_PATTERN = "^\\d{3}-\\d{3,4}-\\d{4}$";

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null 값은 @NotNull 어노테이션으로 처리
        }
        return Pattern.matches(PHONE_PATTERN, value);
    }
} 