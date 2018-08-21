package com.horacehylee.validator.validator.testobject;

public class TestObject {
    private String id;
    private Integer num;

    public TestObject() {

    }

    public TestObject(String id, Integer num) {
        this.id = id;
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public Integer getNum() {
        return num;
    }
}
