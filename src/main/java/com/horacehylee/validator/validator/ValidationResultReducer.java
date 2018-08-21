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
        aggregateResult.setFailedExpressionsMap(
                mergeFailedExpressionMap(aggregateResult.getFailedExpressionsMap(), validationResult.getFailedExpressionsMap())
        );
        return aggregateResult;
    }

    static Map<Class<?>, List<String>> mergeFailedExpressionMap(
            Map<Class<?>, List<String>> m1,
            Map<Class<?>, List<String>> m2
    ) {
        return Stream.of(m1, m2)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (aggregate, strings) -> {
                            strings.stream()
                                    .filter((stringObj) -> !aggregate.contains(stringObj))
                                    .forEach(aggregate::add);
                            return aggregate;
                        }
                ));
    }
}
