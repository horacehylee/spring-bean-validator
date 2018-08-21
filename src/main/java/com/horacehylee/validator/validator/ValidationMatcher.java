package com.horacehylee.validator.validator;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationMatcher implements IValidator {

    private String targetProperty;
    private String matchingProperty;
    private Map<String, List<IValidator>> cases;

    @Override
    public ValidationResult validate(ValidationContext context, Object source) throws IllegalArgumentException {
        Object matching = ValidationUtil.getTargetProperty(matchingProperty, source);
        if (!(matching instanceof String)) {
            throw new IllegalArgumentException("matchingProperty is not a string instance");
        }
        Object target = ValidationUtil.getTargetProperty(targetProperty, source);
        List<IValidator> validators = cases.getOrDefault(matching, new ArrayList<>());
        return validators.stream()
                .map(validator -> validator.validate(
                        context.addTargetProperty(targetProperty)
                                .appendToSourcePath("[" + matchingProperty + "=\"" + matching + "\"]"),
                        target)
                )
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

    public String getMatchingProperty() {
        return matchingProperty;
    }

    public void setMatchingProperty(String matchingProperty) {
        this.matchingProperty = matchingProperty;
    }
}
