package com.solvd.swapi.films;

import com.solvd.swapi.common.AbstractPaginatedAPIMethod;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import io.restassured.response.Response;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/swapi/films/getPaginatedFilms/rq.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class GetPaginatedFilmsMethod extends AbstractPaginatedAPIMethod {

    @Override
    public String getEndCursor(Response response) {
        return response.jsonPath().getString("data.allFilms.pageInfo.endCursor");
    }

    @Override
    public String getStartCursor(Response response) {
        return response.jsonPath().getString("data.allFilms.pageInfo.startCursor");
    }

    @Override
    public boolean hasNextPage(Response response) {
        return response.jsonPath().getBoolean("data.allFilms.pageInfo.hasNextPage");
    }

    @Override
    public boolean hasPreviousPage(Response response) {
        return response.jsonPath().getBoolean("data.allFilms.pageInfo.hasPreviousPage");
    }
}
