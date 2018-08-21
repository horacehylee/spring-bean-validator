package com.horacehylee.validator.validator;

public class FailedValidation {
    private final Object source;
    private final String expression;

    FailedValidation(Object source, String expression) {
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
        if ((this.source == null) ? (other.source != null) : !this.source.equals(other.source)) {
            return false;
        }

        return this.expression.equals(other.expression);
    }
}
