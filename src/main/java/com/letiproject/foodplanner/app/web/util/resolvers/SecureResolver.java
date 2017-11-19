package com.letiproject.foodplanner.app.web.util.resolvers;

/*
 * Created by @belrbeZ on 28.03.2017.
 */

/**
 * Default Comment
 */
public class SecureResolver {
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    // In Seconds
    public static final Integer ACCESS_TOKEN_PERIOD = 240;
    public static final Integer REFRESH_TOKEN_PERIOD = 3600;
}
