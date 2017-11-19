package com.letiproject.foodplanner.app.mongo.domain;


public class Step {

    private int step_number;
    private String description;

    public Step(int step_number, String description) {
        this.step_number = step_number;
        this.description = description;
    }

    public int getNumber() {
        return step_number;
    }

    public void setNumber(int number) {
        this.step_number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
