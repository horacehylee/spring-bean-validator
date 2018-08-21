package com.horacehylee.validator.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayValidator implements IValidator {

    private String targetProperty;
    private List<IValidator> validators;

    @Override
    public ValidationResult validate(ValidationContext context, Object source) {
        Object target = ValidationUtil.getTargetProperty(targetProperty, source);
        if (!target.getClass().isArray() && !List.class.isAssignableFrom(target.getClass())) {
            throw new IllegalArgumentException("targetProperty \"" + targetProperty + "\" is not an array or collection instance");
        }

        List<Object> targets;
        if (target.getClass().isArray()) {
            targets = new ArrayList<>(Collections.singletonList(target));
        } else {
            targets = (List<Object>) target;
        }

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
}
