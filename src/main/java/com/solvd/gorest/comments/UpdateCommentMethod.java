package com.solvd.gorest.comments;

import com.solvd.gorest.AuthorizedApiMethod;
import com.zebrunner.carina.api.annotation.*;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/graphql/comments/updateComment/rq.json")
@ResponseTemplatePath(path = "api/graphql/comments/updateComment/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class UpdateCommentMethod extends AuthorizedApiMethod {


    public void setId(int id) {
        getProperties().setProperty("id", String.valueOf(id));
    }

    public void setEmail(String email) {
        getProperties().setProperty("email", email);
    }

    public void setName(String name) {
        getProperties().setProperty("name", name);
    }

    public void setBody(String body) {
        getProperties().setProperty("body", body);
    }

}
