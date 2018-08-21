package com.horacehylee.validator.validator;

public interface IValidator {
    ValidationResult validate(ValidationContext context, Object source);
}
