package com.solvd.swapi.people;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import io.restassured.response.Response;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/swapi/people/getInvalidField/rq.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.BAD_REQUEST_400)
public class GetInvalidFieldMethod extends AbstractApiMethodV2 {

    public String getErrorMessage(Response response) {
        return response.jsonPath().getString("errors[0].message");
    }
}
