package com.solvd.swapi.people;

import com.solvd.swapi.models.Person;
import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import io.restassured.response.Response;

import java.util.List;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/swapi/people/getAll/rq.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class GetAllPeopleMethod extends AbstractApiMethodV2 {
    private static final String JSON_PATH = "data.allPeople.people";

    public static List<Person> getPersonList(Response response) {
        return response.jsonPath().getList(JSON_PATH, Person.class);
    }
}
