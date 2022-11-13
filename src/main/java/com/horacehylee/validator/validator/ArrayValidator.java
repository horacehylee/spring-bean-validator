package com.horacehylee.validator.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayValidator implements IValidator {

    private String targetProperty;
    private List<IValidator> validators;

    @Override
    public ValidationResult validate(ValidationContext context, Object source) {
        List<Object> targets = validateAndConvertToCollection(ValidationUtil.getTargetProperty(targetProperty, source));

        List<ValidationResult> validationResults = new ArrayList<>();
        for (int i = 0; i < targets.size(); i++) {
            Object targetObj = targets.get(i);
            for (IValidator validator : validators) {
                validationResults.add(
                        validator.validate(
                                context.addTargetProperty(targetProperty).appendToSourcePath("[" + i + "]"),
                                targetObj
                        )
                );
            }
        }
        return validationResults.stream().reduce(new ValidationResult(), ValidationResultReducer::reduce);
    }

    public String getTargetProperty() {
        return targetProperty;
    }

    public void setTargetProperty(String targetProperty) {
        this.targetProperty = targetProperty;
    }

    public List<IValidator> getValidators() {
        return validators;
    }

    public void setValidators(List<IValidator> validators) {
        this.validators = validators;
    }

    private List<Object> validateAndConvertToCollection(Object source) {
        if (source.getClass().isArray()) {
            return new ArrayList<>(Collections.singletonList(source));
        } else if (List.class.isAssignableFrom(source.getClass())) {
            return (List<Object>) source;
        } else {
            throw new IllegalArgumentException("targetProperty \"" + targetProperty + "\" is not an array or collection instance");
        }
    }
}
