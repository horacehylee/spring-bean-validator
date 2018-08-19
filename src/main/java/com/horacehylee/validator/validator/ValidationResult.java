package com.horacehylee.validator.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationResult {

    private boolean valid = true;

    private Map<Class<?>, List<String>> failedExpressionsMap = new HashMap<>();

    ValidationResult() {
    }

    ValidationResult(boolean valid, Map<Class<?>, List<String>> failedExpressionsMap) {
        this.valid = valid;
        this.failedExpressionsMap = failedExpressionsMap;
    }

    public boolean isValid() {
        return valid;
    }

    void invalidate() {
        this.valid = false;
    }

    private List<String> getFailedExpressions(Class<?> clazz) {
        return failedExpressionsMap.getOrDefault(clazz, new ArrayList<>());
    }

    void addFailedExpression(Class<?> clazz, String expression) {
        List<String> failedExpressions = getFailedExpressions(clazz);
        failedExpressions.add(expression);
        failedExpressionsMap.put(clazz, failedExpressions);
    }
}
