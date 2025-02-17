package com.solvd.gorest.todos;

import com.solvd.gorest.AuthorizedApiMethod;
import com.solvd.gorest.utils.IResponseMethods;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.ResponseTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/graphql/todos/deleteTodo/rq.json")
@ResponseTemplatePath(path = "api/graphql/todos/deleteTodo/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class DeleteTodoMethod extends AuthorizedApiMethod implements IResponseMethods {
    public void setIdProperty(String id) {
        getProperties().setProperty("id", id);
    }
}
