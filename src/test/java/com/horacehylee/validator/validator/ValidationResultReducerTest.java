package com.horacehylee.validator.validator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ValidationResultReducerTest {

    @Test
    public void mergeFailedExpressionMapTest() {
        Map<Class<?>, List<String>> m1 = new HashMap<Class<?>, List<String>>() {
            {
                put(TestObject.class, new ArrayList<String>() {
                    {
                        add("id != null");
                    }
                });
            }
        };
        Map<Class<?>, List<String>> m2 = new HashMap<Class<?>, List<String>>() {
            {
                put(TestObject.class, new ArrayList<String>() {
                    {
                        add("num > 0");
                    }
                });
            }
        };
        assertThat(ValidationResultReducer.mergeFailedExpressionMap(m1, m2)).isEqualToComparingFieldByFieldRecursively(
                new HashMap<Class<?>, List<String>>() {
                    {
                        put(TestObject.class, new ArrayList<String>() {
                            {
                                add("id != null");
                                add("num > 0");
                            }
                        });
                    }
                }
        );
    }

    @Test
    public void mergeFailedExpressionMapTest_differentClassKey() {
        Map<Class<?>, List<String>> m1 = new HashMap<Class<?>, List<String>>() {
            {
                put(TestObject.class, new ArrayList<String>() {
                    {
                        add("id != null");
                    }
                });
            }
        };
        Map<Class<?>, List<String>> m2 = new HashMap<Class<?>, List<String>>() {
            {
                put(TestObjectWrapper.class, new ArrayList<String>() {
                    {
                        add("num > 0");
                    }
                });
            }
        };
        assertThat(ValidationResultReducer.mergeFailedExpressionMap(m1, m2)).isEqualToComparingFieldByFieldRecursively(
                new HashMap<Class<?>, List<String>>() {
                    {
                        put(TestObject.class, new ArrayList<String>() {
                            {
                                add("id != null");
                            }
                        });
                        put(TestObjectWrapper.class, new ArrayList<String>() {
                            {
                                add("num > 0");
                            }
                        });
                    }
                }
        );
    }

    @Test
    public void mergeFailedExpressionMapTest_duplicateValues() {
        Map<Class<?>, List<String>> m1 = new HashMap<Class<?>, List<String>>() {
            {
                put(TestObject.class, new ArrayList<String>() {
                    {
                        add("id != null");
                        add("num > 0");
                    }
                });
            }
        };
        Map<Class<?>, List<String>> m2 = new HashMap<Class<?>, List<String>>() {
            {
                put(TestObject.class, new ArrayList<String>() {
                    {
                        add("id != null");
                        add("num > 0");
                    }
                });
            }
        };
        assertThat(ValidationResultReducer.mergeFailedExpressionMap(m1, m2)).isEqualToComparingFieldByFieldRecursively(
                new HashMap<Class<?>, List<String>>() {
                    {
                        put(TestObject.class, new ArrayList<String>() {
                            {
                                add("id != null");
                                add("num > 0");
                            }
                        });
                    }
                }
        );
    }

}