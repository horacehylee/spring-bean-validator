package com.horacehylee.validator.validator;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

public class ValidationUtil {

    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    public static Object getTargetProperty(
            String targetProperty,
            Object source
    ) {
        if (targetProperty == null || StringUtils.isEmpty(targetProperty.trim())) {
            return source;
        } else {
            Expression targetExpression = EXPRESSION_PARSER.parseExpression(targetProperty);
            return targetExpression.getValue(source);
        }
    }
}
