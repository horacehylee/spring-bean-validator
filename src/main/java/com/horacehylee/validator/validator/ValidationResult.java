package com.horacehylee.validator.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ValidationResult {

    private boolean valid = true;

    private Map<Integer, List<FailedValidation>> failedValidationsMap = new HashMap<>();

    ValidationResult() {
    }

    ValidationResult(boolean valid, Map<Integer, List<FailedValidation>> failedExpressionsMap) {
        this.valid = valid;
        this.failedValidationsMap = failedExpressionsMap;
    }

    boolean isValid() {
        return valid;
    }

    void invalidate() {
        this.valid = false;
    }

    Map<Integer, List<FailedValidation>> getFailedValidationsMap() {
        return failedValidationsMap;
    }

    void setFailedValidationsMap(Map<Integer, List<FailedValidation>> failedValidationsMap) {
        this.failedValidationsMap = failedValidationsMap;
    }

    void addFailedValidation(Object source, String expression) {
        int sourceHashCode = System.identityHashCode(source);
        List<FailedValidation> failedValidations = failedValidationsMap.getOrDefault(sourceHashCode, new ArrayList<FailedValidation>() {
        });
        failedValidations.add(new FailedValidation(source, expression));
        failedValidationsMap.put(sourceHashCode, failedValidations);
    }
}
