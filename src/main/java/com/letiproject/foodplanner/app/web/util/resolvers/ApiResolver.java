package com.letiproject.foodplanner.app.web.util.resolvers;


public class ApiResolver {
    private static final String API = "/api";
    private static final String API_PRIVATE = WebResolver.SECURED + API;
    public static final String PROFILE = API_PRIVATE + "/profile";
    public static final String TASK = API_PRIVATE + "/tasks";
    public static final String ZONE = API_PRIVATE + "/zone";
    public static final String ROUTE = API_PRIVATE + "/routes";
    public static final String GEO = API_PRIVATE + "/geo";
    public static final String FEED = API_PRIVATE + "/feed";
    private static final String API_PUBLIC = "/public" + API;
    public static final String PUBLIC_PROFILE = API_PUBLIC + "/profile";
    private static final String API_VERSION = "/v1.0";
}
