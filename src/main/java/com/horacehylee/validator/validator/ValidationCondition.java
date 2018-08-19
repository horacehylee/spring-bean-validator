package com.horacehylee.validator.validator;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationCondition implements IValidator {
    private String targetProperty;
    private String conditions;
    private ExpressionParser expressionParser;
    private List<Expression> expressions;

    public ValidationCondition() {
        expressionParser = new SpelExpressionParser();
        expressions = new ArrayList<>();
    }

    @Override
    public ValidationResult validate(Object source) {
        Object target = ValidationUtil.getTargetProperty(targetProperty, expressionParser, source);
        ValidationResult result = new ValidationResult();
        for (Expression expression : expressions) {
            boolean valid = expression.getValue(target, Boolean.class);
            if (!valid) {
                result.invalidate();
                result.addFailedExpression(target.getClass(), expression.getExpressionString());
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

    List<Expression> getExpressions() {
        return expressions;
    }

    public String getTargetProperty() {
        return targetProperty;
    }

    public void setTargetProperty(String targetProperty) {
        this.targetProperty = targetProperty;
    }
}
