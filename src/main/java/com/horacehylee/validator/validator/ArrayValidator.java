package com.horacehylee.validator.validator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ArrayValidator implements IValidator {

    private String targetProperty;
    private List<IValidator> validators;

    @Override
    public ValidationResult validate(Object source) {
        Object target = ValidationUtil.getTargetProperty(targetProperty, source);
        if (!target.getClass().isArray() && !List.class.isAssignableFrom(target.getClass())) {
            throw new IllegalArgumentException("targetProperty \"" + targetProperty + "\" is not an array or collection instance");
        }

        Stream<Object> targetsStream;
        if (target.getClass().isArray()) {
            targetsStream = Arrays.stream(((Object[]) target));
        } else {
            targetsStream = ((List<Object>) target).stream();
        }

        return targetsStream.flatMap(
                targetObj -> validators.stream()
                        .map(validator -> validator.validate(targetObj)))
                .reduce(new ValidationResult(), ValidationResultReducer::reduce);
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
