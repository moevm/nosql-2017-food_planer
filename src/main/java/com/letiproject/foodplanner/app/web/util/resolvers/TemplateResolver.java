package com.letiproject.foodplanner.app.web.util.resolvers;

/*
 * Created by @belrbeZ on 28.03.2017.
 */

/**
 * Default Comment
 */
public class TemplateResolver {

    public static final String REGISTER = "registration";
    public static final String LOGIN = "login";
    public static final String LOGIN_REGISTER = "registrationPage";

    public static final String WELCOME = "welcome";
    public static final String HOME = "home";
    public static final String PROFILE = "profile";
    public static final String TASK = "task";
    public static final String FEED = "feed";
    public static final String MAP = "map";
    public static final String TASK_FORM = "taskform";
    public static final String GEOPOINT_FORM = "geopointform";
    public static final String MENU_FORM = "menuForm";

    public static String redirect(String viewName) {
        return "redirect:" + viewName;
    }
}
