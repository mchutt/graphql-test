package com.solvd.swapi.people;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import io.restassured.response.Response;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/swapi/people/getPerson/rq.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class GetPersonByIdMethod extends AbstractApiMethodV2 {
    private static final String JSON_PATH = "data.person";

    public void setId(String id) {
        getProperties().setProperty("id", id);
    }

    public static Person getPerson(Response response) {
        return response.jsonPath().getObject(JSON_PATH, Person.class);
    }
}
