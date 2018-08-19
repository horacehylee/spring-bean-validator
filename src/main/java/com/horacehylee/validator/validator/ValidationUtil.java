package com.horacehylee.validator.validator;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.util.StringUtils;

public class ValidationUtil {

    public static Object getTargetProperty(
            String targetProperty,
            ExpressionParser expressionParser,
            Object source
    ) {
        if (targetProperty == null || StringUtils.isEmpty(targetProperty.trim())) {
            return source;
        } else {
            Expression targetExpression = expressionParser.parseExpression(targetProperty);
            return targetExpression.getValue(source);
        }
    }
}
