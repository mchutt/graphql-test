package com.solvd.gorest.utils;

import com.jayway.jsonpath.JsonPath;

public interface IResponseMethods {
    default String getErrorMessage(String response) {
        return JsonPath.read(response, "$.errors[0].message");
    }
}
