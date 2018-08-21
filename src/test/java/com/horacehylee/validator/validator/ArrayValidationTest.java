package com.horacehylee.validator.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horacehylee.validator.validator.testobject.TestObject;
import com.horacehylee.validator.validator.testobject.TestObjectListWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {
        "classpath:/validation-condition.xml",
        "classpath:/array-validator.xml",
})
public class ArrayValidationTest {

    @Value("${classpath:/sample/arrayValidator.sample.json}")
    private Resource sampleJsonResource;

    @Autowired
    @Qualifier("arrayValidator")
    private ArrayValidator arrayValidator;

    @Test
    public void arrayValidatorTest() throws Exception {
        File file = new File(sampleJsonResource.getURI().getPath());
        TestObjectListWrapper testObjectListWrapper = new ObjectMapper().readValue(file, TestObjectListWrapper.class);
        ValidationResult result = arrayValidator.validate(testObjectListWrapper);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(
                new ValidationResult(false, new HashMap<Integer, List<FailedValidation>>() {
                    {
                        put(testObjectListWrapper.getTestObjects().get(0).hashCode(), new ArrayList<FailedValidation>() {
                            {
                                {
                                    add(new FailedValidation(testObjectListWrapper.getTestObjects().get(0), "id != null"));
                                }
                            }
                        });
                        put(testObjectListWrapper.getTestObjects().get(1).hashCode(), new ArrayList<FailedValidation>() {
                            {
                                {
                                    add(new FailedValidation(
                                            testObjectListWrapper.getTestObjects().get(1),
                                            "id != null")
                                    );
                                    add(new FailedValidation(
                                            testObjectListWrapper.getTestObjects().get(1),
                                            "num > 0")
                                    );
                                }
                            }
                        });
                    }
                })
        );
    }
}