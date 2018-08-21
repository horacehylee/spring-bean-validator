package com.horacehylee.validator.validator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValidationResultReducer {

    static ValidationResult reduce(ValidationResult aggregateResult, ValidationResult validationResult) {
        if (validationResult.isValid()) {
            return aggregateResult;
        }

        aggregateResult.invalidate();
        aggregateResult.setFailedValidationsMap(
                reduceFailedValidationsMap(aggregateResult.getFailedValidationsMap(), validationResult.getFailedValidationsMap())
        );
        return aggregateResult;
    }

    static Map<Integer, List<FailedValidation>> reduceFailedValidationsMap(
            Map<Integer, List<FailedValidation>> m1,
            Map<Integer, List<FailedValidation>> m2
    ) {
        return Stream.of(m1, m2)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (aggregateFailedValidations, failedValidations) -> {
                            failedValidations.stream()
                                    .filter((failedValidation) -> !aggregateFailedValidations.contains(failedValidation))
                                    .forEach(aggregateFailedValidations::add);
                            return aggregateFailedValidations;
                        }
                ));
    }
}
