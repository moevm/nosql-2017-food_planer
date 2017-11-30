package com.letiproject.foodplanner.app.web.util.resolvers;

/*
 * Created by belrbeZ on 26.03.2017.
 */

/**
 * Stores all paths for Controllers and Rest End Points
 */
public class WebResolver {
    public static final String SECURED = "/secure";
    public static final String ERROR = "/error";
    public static final String DENIED = "/denied";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String WELCOME = "/welcome";
    public static final String MENU_FORM = "/menuForm";
    public static final String PROFILE = SECURED + "/profile";
    public static final String LOGOUT = SECURED + "/logout";
    public static final String ADMIN = "/admin";
    private static final String OAUTH = "/oauth";
    private static final String TOKEN = "/token";
    public static final String MENU = "/menu/**";
}
