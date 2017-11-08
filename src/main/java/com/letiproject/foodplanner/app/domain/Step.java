package com.letiproject.foodplanner.app.domain;


public class Step {

    private static int numberCounter = 1;

    private int number;
    private String description;

    public Step(String description) {
        this.number = numberCounter++;
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
