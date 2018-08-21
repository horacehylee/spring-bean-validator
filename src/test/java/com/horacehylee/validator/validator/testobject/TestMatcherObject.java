package com.horacehylee.validator.validator.testobject;

public class TestMatcherObject {
    private String key;
    private String id;
    private int num;

    public TestMatcherObject(String key, String id, int num) {
        this.key = key;
        this.id = id;
        this.num = num;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
