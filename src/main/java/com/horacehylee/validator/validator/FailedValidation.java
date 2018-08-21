package com.horacehylee.validator.validator;

import java.util.Objects;

public class FailedValidation {
    private final Object source;
    private final String expression;
    private final ValidationContext context;

    FailedValidation(ValidationContext context, Object source, String expression) {
        this.context = context;
        this.source = source;
        this.expression = expression;
    }

    public Object getSource() {
        return source;
    }

    public String getExpression() {
        return expression;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!FailedValidation.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final FailedValidation other = (FailedValidation) obj;
        return Objects.equals(this.source, other.source)
                && this.expression.equals(other.expression)
                && this.context.equals(other.context);
    }
}
