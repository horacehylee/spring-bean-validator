package com.horacehylee.validator.validator;

import com.horacehylee.validator.validator.testobject.TestObject;
import com.horacehylee.validator.validator.testobject.TestObjectNestedWrapper;
import com.horacehylee.validator.validator.testobject.TestObjectWrapper;
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
        TestObject t1 = new TestObject("42", 1);
        assertThat(basicValidator.validate(t1)).isEqualToComparingFieldByFieldRecursively(
                new ValidationResult(true, new HashMap<Integer, List<FailedValidation>>() {
                })
        );

        TestObject t2 = new TestObject(null, 1);
        assertThat(basicValidator.validate(t2)).isEqualToComparingFieldByFieldRecursively(
                new ValidationResult(false, new HashMap<Integer, List<FailedValidation>>() {
                    {
                        put(t2.hashCode(), new ArrayList<FailedValidation>() {
                            {
                                add(new FailedValidation(
                                        t2,
                                        "id != null")
                                );
                            }
                        });
                    }
                })
        );

        TestObject t3 = new TestObject("42", 0);
        assertThat(basicValidator.validate(t3)).isEqualToComparingFieldByFieldRecursively(
                new ValidationResult(false, new HashMap<Integer, List<FailedValidation>>() {
                    {
                        put(t3.hashCode(), new ArrayList<FailedValidation>() {
                            {
                                add(new FailedValidation(
                                        t3,
                                        "num > 0")
                                );
                            }
                        });
                    }
                })
        );

        TestObject t4 = new TestObject(null, 0);
        assertThat(basicValidator.validate(t4)).isEqualToComparingFieldByFieldRecursively(
                new ValidationResult(false, new HashMap<Integer, List<FailedValidation>>() {
                    {
                        put(t4.hashCode(), new ArrayList<FailedValidation>() {
                            {
                                add(new FailedValidation(
                                        t4,
                                        "id != null")
                                );
                                add(new FailedValidation(
                                        t4,
                                        "num > 0")
                                );
                            }
                        });
                    }
                })
        );
    }

    @Test
    public void basicValidationTest_withTargetProperty() {
        TestObjectWrapper t1 = new TestObjectWrapper(new TestObject("42", 1));
        assertThat(basicValidatorWithTargetProperty.validate(t1))
                .isEqualToComparingFieldByFieldRecursively(
                        new ValidationResult(true, new HashMap<Integer, List<FailedValidation>>() {
                        })
                );

        TestObjectWrapper t2 = new TestObjectWrapper(new TestObject(null, 0));
        assertThat(basicValidatorWithTargetProperty.validate(t2))
                .isEqualToComparingFieldByFieldRecursively(
                        new ValidationResult(false, new HashMap<Integer, List<FailedValidation>>() {
                            {
                                put(t2.getTestObject().hashCode(), new ArrayList<FailedValidation>() {
                                    {
                                        add(new FailedValidation(
                                                t2.getTestObject(),
                                                "id != null")
                                        );
                                        add(new FailedValidation(
                                                t2.getTestObject(),
                                                "num > 0")
                                        );
                                    }
                                });
                            }
                        })
                );

        TestObjectNestedWrapper t3 = new TestObjectNestedWrapper(new TestObjectWrapper(new TestObject("42", 1)));
        assertThat(basicValidatorWithNestedTargetProperty.validate(t3))
                .isEqualToComparingFieldByFieldRecursively(
                        new ValidationResult(true, new HashMap<Integer, List<FailedValidation>>() {
                        })
                );

        TestObjectNestedWrapper t4 = new TestObjectNestedWrapper(new TestObjectWrapper(new TestObject(null, 0)));
        assertThat(basicValidatorWithNestedTargetProperty.validate(t4))
                .isEqualToComparingFieldByFieldRecursively(
                        new ValidationResult(false, new HashMap<Integer, List<FailedValidation>>() {
                            {
                                put(t4.getTestObjectWrapper().getTestObject().hashCode(), new ArrayList<FailedValidation>() {
                                    {
                                        add(new FailedValidation(
                                                t4.getTestObjectWrapper().getTestObject(),
                                                "id != null")
                                        );
                                        add(new FailedValidation(
                                                t4.getTestObjectWrapper().getTestObject(),
                                                "num > 0")
                                        );
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