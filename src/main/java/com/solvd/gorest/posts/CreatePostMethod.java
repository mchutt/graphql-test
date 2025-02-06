package com.solvd.gorest.posts;

import com.solvd.gorest.AuthorizedApiMethod;
import com.zebrunner.carina.api.annotation.*;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/graphql/posts/createPost/rq.json")
@ResponseTemplatePath(path = "api/graphql/posts/createPost/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
@PropertiesPath(path = "api/graphql/posts/post.properties")
public class CreatePostMethod extends AuthorizedApiMethod {
}
