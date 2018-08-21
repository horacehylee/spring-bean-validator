package com.horacehylee.validator.validator.testobject;

public class TestObjectNestedWrapper {
    private TestObjectWrapper testObjectWrapper;

    public TestObjectNestedWrapper(TestObjectWrapper testObjectWrapper) {
        this.testObjectWrapper = testObjectWrapper;
    }

    public TestObjectWrapper getTestObjectWrapper() {
        return testObjectWrapper;
    }

    public void setTestObjectWrapper(TestObjectWrapper testObjectWrapper) {
        this.testObjectWrapper = testObjectWrapper;
    }
}
