package com.horacehylee.validator.validator;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.expression.Expression;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/validation-condition.xml"})
public class ValidationConditionTest {

    @Autowired
    @Qualifier("basicValidator")
    private ValidationCondition basicValidator;

    @Autowired
    @Qualifier("basicValidatorWithUnknownProperty")
    private ValidationCondition basicValidatorWithUnkownProperty;

    @Autowired
    @Qualifier("basicValidatorWithTargetProperty")
    private ValidationCondition basicValidatorWithTargetProperty;

    @Autowired
    @Qualifier("basicValidatorWithNestedTargetProperty")
    private ValidationCondition basicValidatorWithNestedTargetProperty;

    @Test
    public void basicValidatorTest_buildExpressions() {
        List<String> expressions = basicValidator.getExpressions().stream()
                .map(Expression::getExpressionString)
                .collect(Collectors.toList());
        assertThat(expressions).containsExactly("id != null", "num > 0");
    }

    @Test
    public void basicValidatorTest() {
        assertThat(basicValidator.validate(new TestObject("42", 1))).isEqualToComparingFieldByFieldRecursively(
                new ValidationResult(true, new HashMap<Class<?>, List<String>>() {
                })
        );
        assertThat(basicValidator.validate(new TestObject(null, 1))).isEqualToComparingFieldByFieldRecursively(
                new ValidationResult(false, new HashMap<Class<?>, List<String>>() {
                    {
                        put(TestObject.class, new ArrayList<String>() {
                            {
                                add("id != null");
                            }
                        });
                    }
                })
        );
        assertThat(basicValidator.validate(new TestObject("42", 0))).isEqualToComparingFieldByFieldRecursively(
                new ValidationResult(false, new HashMap<Class<?>, List<String>>() {
                    {
                        put(TestObject.class, new ArrayList<String>() {
                            {
                                add("num > 0");
                            }
                        });
                    }
                })
        );
        assertThat(basicValidator.validate(new TestObject(null, 0))).isEqualToComparingFieldByFieldRecursively(
                new ValidationResult(false, new HashMap<Class<?>, List<String>>() {
                    {
                        put(TestObject.class, new ArrayList<String>() {
                            {
                                add("id != null");
                                add("num > 0");
                            }
                        });
                    }
                })
        );
    }

    @Test
    public void basicValidationTest_withTargetProperty() {
        assertThat(basicValidatorWithTargetProperty.validate(new TestObjectWrapper(new TestObject("42", 1))))
                .isEqualToComparingFieldByFieldRecursively(
                        new ValidationResult(true, new HashMap<Class<?>, List<String>>() {
                        })
                );

        assertThat(basicValidatorWithTargetProperty.validate(new TestObjectWrapper(new TestObject(null, 0))))
                .isEqualToComparingFieldByFieldRecursively(
                        new ValidationResult(false, new HashMap<Class<?>, List<String>>() {
                            {
                                put(TestObject.class, new ArrayList<String>() {
                                    {
                                        add("id != null");
                                        add("num > 0");
                                    }
                                });
                            }
                        })
                );

        assertThat(basicValidatorWithNestedTargetProperty.validate(
                new TestObjectNestedWrapper(new TestObjectWrapper(new TestObject("42", 1)))))
                .isEqualToComparingFieldByFieldRecursively(
                        new ValidationResult(true, new HashMap<Class<?>, List<String>>() {
                        })
                );

        assertThat(basicValidatorWithNestedTargetProperty.validate(
                new TestObjectNestedWrapper(new TestObjectWrapper(new TestObject(null, 0)))))
                .isEqualToComparingFieldByFieldRecursively(
                        new ValidationResult(false, new HashMap<Class<?>, List<String>>() {
                            {
                                put(TestObject.class, new ArrayList<String>() {
                                    {
                                        add("id != null");
                                        add("num > 0");
                                    }
                                });
                            }
                        })
                );
    }

    @Test
    public void basicValidatorTest_unknownProperty() {
        assertThatThrownBy(() -> {
            basicValidatorWithUnkownProperty.validate(new TestObject("42", 1));
        }).hasMessageContaining("Property or field 'unknownProp' cannot be found on object");
    }
}