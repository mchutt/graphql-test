package com.solvd.gorest.services;

import com.solvd.gorest.utils.APIConstants;

public class AuthService {
    public static String getAuthToken(){
        return APIConstants.TOKEN;
    }
}
