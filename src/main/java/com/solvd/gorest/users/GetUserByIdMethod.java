package com.solvd.gorest.users;

import com.solvd.gorest.AuthorizedApiMethod;
import com.solvd.gorest.utils.IResponseMethods;
import com.zebrunner.carina.api.annotation.*;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/graphql/users/getUser/rq.json")
@ResponseTemplatePath(path = "api/graphql/users/getUser/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class GetUserByIdMethod extends AuthorizedApiMethod implements IResponseMethods {
}
