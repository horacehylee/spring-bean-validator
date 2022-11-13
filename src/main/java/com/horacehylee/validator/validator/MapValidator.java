package com.horacehylee.validator.validator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapValidator implements IValidator {

    private String targetProperty;
    private List<IValidator> validators;

    @Override
    public ValidationResult validate(ValidationContext context, Object source) {
        Map<Object, Object> targets = validateAndConvertMap(ValidationUtil.getTargetProperty(targetProperty, source));

        return targets.entrySet().stream()
                .map(entry ->
                        validators.stream()
                                .map(validator -> validator.validate(
                                        context.addTargetProperty(targetProperty).appendToSourcePath("['" + entry.getKey() + "']"),
                                                entry.getValue()
                                        )
                                )
                                .collect(Collectors.toList())
                )
                .flatMap(List::stream)
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

    private Map<Object, Object> validateAndConvertMap(Object source) {
        if (Map.class.isAssignableFrom(source.getClass())) {
            return (Map<Object, Object>) source;
        } else {
            throw new IllegalArgumentException("targetProperty \"" + targetProperty + "\" is not a map instance");
        }
    }
}
