package com.horacehylee.validator.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horacehylee.validator.validator.testobject.TestObjectMapWrapper;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {
        "classpath:/validation-condition.xml",
        "classpath:/map-validator.xml",
})
public class MapValidationTest {

    @Value("${classpath:/sample/mapValidator.sample.json}")
    private Resource sampleJsonResource;

    @Autowired
    @Qualifier("mapValidator")
    private MapValidator mapValidator;

    @Test
    public void mapValidatorTest() throws Exception {
        File file = new File(sampleJsonResource.getURI().getPath());
        TestObjectMapWrapper testObjectMapWrapper = new ObjectMapper().readValue(file, TestObjectMapWrapper.class);
        ValidationResult result = mapValidator.validate(
                new ValidationContext("testObjectMapWrapper"),
                testObjectMapWrapper
        );

        assertThat(result).isEqualToComparingFieldByFieldRecursively(
                new ValidationResult(false, new HashMap<Integer, List<FailedValidation>>() {
                    {
                        put(testObjectMapWrapper.getTestObjectMap().get("key").hashCode(), new ArrayList<FailedValidation>() {
                            {
                                add(new FailedValidation(new ValidationContext("testObjectMapWrapper.testObjectMap['key']"),
                                        testObjectMapWrapper.getTestObjectMap().get("key"),
                                        "id != null")
                                );
                            }
                        });
                        put(testObjectMapWrapper.getTestObjectMap().get("key2").hashCode(), new ArrayList<FailedValidation>() {
                            {
                                add(new FailedValidation(new ValidationContext("testObjectMapWrapper.testObjectMap['key2']"),
                                        testObjectMapWrapper.getTestObjectMap().get("key2"),
                                        "id != null")
                                );
                                add(new FailedValidation(
                                        new ValidationContext("testObjectMapWrapper.testObjectMap['key2']"),
                                        testObjectMapWrapper.getTestObjectMap().get("key2"),
                                        "num > 0")
                                );
                            }
                        });
                    }
                })
        );
    }
}
