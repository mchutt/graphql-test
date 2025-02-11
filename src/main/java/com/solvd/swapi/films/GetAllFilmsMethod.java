package com.solvd.swapi.films;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import io.restassured.response.Response;

import java.util.List;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/swapi/films/getAll/rq.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class GetAllFilmsMethod extends AbstractApiMethodV2 {

    // using jsonPath from rest-assured instead of from jayway
    public List<Film> getAllFilms(Response response){
        return response.jsonPath().getList("data.allFilms.films", Film.class);
    }
}
