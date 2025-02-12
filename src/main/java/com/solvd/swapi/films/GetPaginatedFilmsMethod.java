package com.solvd.swapi.films;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import io.restassured.response.Response;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/swapi/films/getPaginatedFilms/rq.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class GetPaginatedFilmsMethod extends AbstractApiMethodV2 {
    //Request helpers
    public void setFirst(int first) {
        getProperties().setProperty("first", String.valueOf(first));
    }

    public void setAfter(String after) {
        getProperties().setProperty("after", after);
    }


    // Response helpers
    public String getEndCursor(Response response) {
        return response.jsonPath().getString("data.allFilms.pageInfo.endCursor");
    }

    public boolean hasNextPage(Response response) {
        return response.jsonPath().getBoolean("data.allFilms.pageInfo.hasNextPage");
    }
}
