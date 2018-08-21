package com.horacehylee.validator.validator;

import com.horacehylee.validator.validator.testobject.TestObject;
import com.horacehylee.validator.validator.testobject.TestObjectWrapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ValidationResultReducerTest {

    @Test
    public void mergeFailedExpressionMapTest() {
        Map<Integer, List<FailedValidation>> m1 = new HashMap<Integer, List<FailedValidation>>() {
            {
                put(1, new ArrayList<FailedValidation>() {
                    {
                        add(new FailedValidation(
                                new ValidationContext(""),
                                null,
                                "id != null")
                        );
                    }
                });
            }
        };
        Map<Integer, List<FailedValidation>> m2 = new HashMap<Integer, List<FailedValidation>>() {
            {
                put(1, new ArrayList<FailedValidation>() {
                    {
                        add(new FailedValidation(
                                new ValidationContext(""),
                                null,
                                "num > 0")
                        );
                    }
                });
            }
        };
        assertThat(ValidationResultReducer.reduceFailedValidationsMap(m1, m2)).isEqualToComparingFieldByFieldRecursively(
                new HashMap<Integer, List<FailedValidation>>() {
                    {
                        put(1, new ArrayList<FailedValidation>() {
                            {
                                add(new FailedValidation(
                                        new ValidationContext(""),
                                        null,
                                        "id != null")
                                );
                                add(new FailedValidation(
                                        new ValidationContext(""),
                                        null,
                                        "num > 0")
                                );
                            }
                        });
                    }
                }
        );
    }

    @Test
    public void mergeFailedExpressionMapTest_differentClassKey() {
        Map<Integer, List<FailedValidation>> m1 = new HashMap<Integer, List<FailedValidation>>() {
            {
                put(1, new ArrayList<FailedValidation>() {
                    {
                        add(new FailedValidation(
                                new ValidationContext(""),
                                null,
                                "id != null")
                        );
                    }
                });
            }
        };
        Map<Integer, List<FailedValidation>> m2 = new HashMap<Integer, List<FailedValidation>>() {
            {
                put(2, new ArrayList<FailedValidation>() {
                    {
                        add(new FailedValidation(
                                new ValidationContext(""),
                                null,
                                "num > 0")
                        );
                    }
                });
            }
        };
        assertThat(ValidationResultReducer.reduceFailedValidationsMap(m1, m2)).isEqualToComparingFieldByFieldRecursively(
                new HashMap<Integer, List<FailedValidation>>() {
                    {
                        put(1, new ArrayList<FailedValidation>() {
                            {
                                add(new FailedValidation(
                                        new ValidationContext(""),
                                        null,
                                        "id != null")
                                );
                            }
                        });
                        put(2, new ArrayList<FailedValidation>() {
                            {
                                add(new FailedValidation(
                                        new ValidationContext(""),
                                        null,
                                        "num > 0")
                                );
                            }
                        });
                    }
                }
        );
    }

    @Test
    public void mergeFailedExpressionMapTest_duplicateValues() {
        Map<Integer, List<FailedValidation>> m1 = new HashMap<Integer, List<FailedValidation>>() {
            {
                put(1, new ArrayList<FailedValidation>() {
                    {
                        add(new FailedValidation(
                                new ValidationContext(""),
                                null,
                                "id != null")
                        );
                        add(new FailedValidation(
                                new ValidationContext(""),
                                null,
                                "num > 0")
                        );
                    }
                });
            }
        };
        Map<Integer, List<FailedValidation>> m2 = new HashMap<Integer, List<FailedValidation>>() {
            {
                put(1, new ArrayList<FailedValidation>() {
                    {
                        add(new FailedValidation(
                                new ValidationContext(""),
                                null,
                                "id != null")
                        );
                        add(new FailedValidation(
                                new ValidationContext(""),
                                null,
                                "num > 0")
                        );
                    }
                });
            }
        };
        assertThat(ValidationResultReducer.reduceFailedValidationsMap(m1, m2)).isEqualToComparingFieldByFieldRecursively(
                new HashMap<Integer, List<FailedValidation>>() {
                    {
                        put(1, new ArrayList<FailedValidation>() {
                            {
                                add(new FailedValidation(
                                        new ValidationContext(""),
                                        null,
                                        "id != null")
                                );
                                add(new FailedValidation(
                                        new ValidationContext(""),
                                        null,
                                        "num > 0")
                                );
                            }
                        });
                    }
                }
        );
    }

}