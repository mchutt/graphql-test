package com.solvd.gorest.comments;

import com.solvd.gorest.AuthorizedApiMethod;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.ResponseTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/graphql/comments/deleteComment/rq.json")
@ResponseTemplatePath(path = "api/graphql/comments/deleteComment/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class DeleteCommentMethod extends AuthorizedApiMethod {
    public void setId(int id) {
        getProperties().setProperty("id", String.valueOf(id));
    }
}
