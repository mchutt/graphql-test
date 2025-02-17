package com.solvd.gorest.utils;

public class APIConstants {
    public static final String TOKEN = System.getenv("GO_REST_TOKEN");
    public static final String RESOURCES_PATH = "api/graphql/";

    //Error messages
    public static final String NOT_AUTHENTICATED_MESSAGE = "You need to authenticate your self to perform this action";
    public static final String NOT_FOUND = "Resource not found!";
}
