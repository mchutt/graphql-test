package com.solvd.gorest.comments;

import com.solvd.gorest.AuthorizedApiMethod;
import com.zebrunner.carina.api.annotation.*;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/graphql/comments/createComment/rq.json")
@ResponseTemplatePath(path = "api/graphql/comments/createComment/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class CreateCommentMethod extends AuthorizedApiMethod {

    public void setPostId(int postId){
        getProperties().setProperty("postId", String.valueOf(postId));
    }

    public void setEmail(String email){
        getProperties().setProperty("email", email);
    }
}
