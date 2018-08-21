package com.horacehylee.validator.validator;

import com.horacehylee.validator.validator.testobject.TestMatcherObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/validation-condition.xml", "classpath:/validation-matcher.xml"})
public class ValidationMatcherTest {

    @Autowired
    @Qualifier("validationMatcher")
    private ValidationMatcher validationMatcher;

    @Test
    public void validationMatcherTest() {
        ValidationContext context = new ValidationContext("testMatcherObject");

        TestMatcherObject t1 = new TestMatcherObject("key1", "42", 1);
        assertThat(validationMatcher.validate(context, t1))
                .isEqualToComparingFieldByFieldRecursively(
                        new ValidationResult(true, new HashMap<Integer, List<FailedValidation>>() {
                        })
                );


        TestMatcherObject t2 = new TestMatcherObject("key1", null, 1);
        assertThat(validationMatcher.validate(context, t2))
                .isEqualToComparingFieldByFieldRecursively(
                        new ValidationResult(false, new HashMap<Integer, List<FailedValidation>>() {
                            {
                                put(t2.hashCode(), new ArrayList<FailedValidation>() {
                                    {
                                        add(new FailedValidation(
                                                new ValidationContext("testMatcherObject[key=\"key1\"]"),
                                                t2,
                                                "id != null")
                                        );
                                    }
                                });
                            }
                        })
                );
    }
}
