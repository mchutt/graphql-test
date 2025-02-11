package com.solvd.gorest.posts;

import com.jayway.jsonpath.JsonPath;
import com.solvd.gorest.AuthorizedApiMethod;
import com.zebrunner.carina.api.annotation.*;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/graphql/posts/deletePost/rq.json")
@ResponseTemplatePath(path = "api/graphql/posts/deletePost/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class DeletePostMethod extends AuthorizedApiMethod {

    public static String getErrorMessage(String response) {
        return JsonPath.read(response, "$.errors[0].message");
    }
}
