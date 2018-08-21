package com.horacehylee.validator.validator.testobject;

public class TestObjectWrapper {
    private TestObject testObject;

    public TestObjectWrapper(TestObject testObject) {
        this.testObject = testObject;
    }

    public TestObject getTestObject() {
        return testObject;
    }

    public void setTestObject(TestObject testObject) {
        this.testObject = testObject;
    }
}
