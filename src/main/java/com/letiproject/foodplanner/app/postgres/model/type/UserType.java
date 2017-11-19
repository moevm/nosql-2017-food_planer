package com.letiproject.foodplanner.app.postgres.model.type;

/*
 * Created by belrbeZ on 20.03.2017.
 */

/**
 * Default Comment
 */
public enum UserType {
    UNKNOWN(-1),
    EMPTY(0),
    USER(10),
    ADMIN(999),
    COMMUNITY(20),
    ORGANIZATION(30),
    ENTERPRISE(40);

    private final long value;

    UserType(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
