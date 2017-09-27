package com.letiproject.foodplanner.app.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "test")
public class TestObject {

    @Id
    private String id;
    private String data;

    public TestObject(String id, String data) {
        this.id = id;
        this.data = data;
    }

    public TestObject(String data) {
        this.data = data;
    }

    public TestObject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
