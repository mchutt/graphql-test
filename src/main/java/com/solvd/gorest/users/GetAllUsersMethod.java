package com.solvd.gorest.users;

import com.solvd.gorest.AuthorizedApiMethod;
import com.zebrunner.carina.api.annotation.*;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/graphql/users/getAll/rq.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class GetAllUsersMethod extends AuthorizedApiMethod {
}
