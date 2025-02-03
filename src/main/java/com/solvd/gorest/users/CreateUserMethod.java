package com.solvd.gorest.users;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.PropertiesPath;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/graphql/users/createUser/rq.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
@PropertiesPath(path = "api/graphql/users/user.properties")
public class CreateUserMethod extends AbstractApiMethodV2 {
}

