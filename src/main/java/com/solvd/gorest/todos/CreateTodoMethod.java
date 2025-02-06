package com.solvd.gorest.todos;

import com.solvd.gorest.AuthorizedApiMethod;
import com.zebrunner.carina.api.annotation.*;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/graphql/todos/createTodo/rq.json")
@ResponseTemplatePath(path = "api/graphql/todos/createTodo/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class CreateTodoMethod extends AuthorizedApiMethod {

}