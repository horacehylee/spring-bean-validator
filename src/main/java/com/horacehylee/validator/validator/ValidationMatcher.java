package com.horacehylee.validator.validator;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationMatcher implements IValidator {

    private String targetProperty;
    private ExpressionParser expressionParser;
    private Map<String, List<IValidator>> cases;

    public ValidationMatcher() {
        expressionParser = new SpelExpressionParser();
    }

    @Override
    public ValidationResult validate(Object source) throws IllegalArgumentException {
        Object target = ValidationUtil.getTargetProperty(targetProperty, expressionParser, source);
        if (!(target instanceof String)) {
            throw new IllegalArgumentException("targetProperty is not a string instance");
        }
        List<IValidator> validators = cases.getOrDefault(target, new ArrayList<>());
        return validators.stream()
                .map(validator -> validator.validate(source))
                .reduce()
//                .collect(Collectors.toList());
        return null;
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
