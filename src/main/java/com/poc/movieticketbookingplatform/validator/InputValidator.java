package com.poc.movieticketbookingplatform.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InputValidator implements ConstraintValidator<ValidInput, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Implement validation logic
        return value != null && value.matches("[a-zA-Z0-9 ]+");
    }
}