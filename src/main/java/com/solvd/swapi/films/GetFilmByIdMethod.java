package com.solvd.swapi.films;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import io.restassured.response.Response;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/swapi/films/getFilm/rq.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class GetFilmByIdMethod extends AbstractApiMethodV2 {

    public void setId(String id) {
        getProperties().setProperty("id", id);
    }

    public String getFilmTitle(Response response) {
        String value = response.jsonPath().getString("data.film.title");
        return value != null ? value : "";
    }
}
