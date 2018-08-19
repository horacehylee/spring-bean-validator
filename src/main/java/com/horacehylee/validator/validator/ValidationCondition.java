package com.horacehylee.validator.validator;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationCondition {
    private String targetProperty;
    private String conditions;
    private ExpressionParser expressionParser;
    private List<Expression> expressions;

    public ValidationCondition() {
        expressionParser = new SpelExpressionParser();
        expressions = new ArrayList<>();
    }

    public ValidationResult validate(Object source) {
        if(targetProperty == null || StringUtils.isEmpty(targetProperty.trim())) {
            return validateSourceObject(source);
        } else {
            Expression targetExpression = expressionParser.parseExpression(targetProperty);
            Object targetSource = targetExpression.getValue(source);
            return validateSourceObject(targetSource);
        }
    }

    private ValidationResult validateSourceObject(Object source) {
        ValidationResult result = new ValidationResult();
        for (Expression expression : expressions) {
            boolean valid = expression.getValue(source, Boolean.class);
            if (!valid) {
                result.setValid(false);
                result.addFailedExpression(source.getClass(), expression.getExpressionString());
            }
        }
        return result;
    }

    private void buildExpressions(String conditions) {
        List<String> expressionStrings = Arrays.stream(conditions.split("\\r?\\n"))
                .map(String::trim)
                .filter(condition -> !condition.isEmpty())
                .collect(Collectors.toList());

        expressions = expressionStrings.stream()
                .map(expressionParser::parseExpression)
                .collect(Collectors.toList());
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
        buildExpressions(conditions);
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    public String getTargetProperty() {
        return targetProperty;
    }

    public void setTargetProperty(String targetProperty) {
        this.targetProperty = targetProperty;
    }
}
