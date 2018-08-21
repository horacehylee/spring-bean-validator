package com.horacehylee.validator.validator;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationMatcher implements IValidator {

    private String targetProperty;
    private Map<String, List<IValidator>> cases;

    @Override
    public ValidationResult validate(Object source) throws IllegalArgumentException {
        Object target = ValidationUtil.getTargetProperty(targetProperty, source);
        if (!(target instanceof String)) {
            throw new IllegalArgumentException("targetProperty is not a string instance");
        }
        List<IValidator> validators = cases.getOrDefault(target, new ArrayList<>());
        return validators.stream()
                .map(validator -> validator.validate(source))
                .reduce(new ValidationResult(), ValidationResultReducer::reduce);
    }

    public String getTargetProperty() {
        return targetProperty;
    }

    public void setTargetProperty(String targetProperty) {
        this.targetProperty = targetProperty;
    }

    public Map<String, List<IValidator>> getCases() {
        return cases;
    }

    public void setCases(Map<String, List<IValidator>> cases) {
        this.cases = cases;
    }
}
