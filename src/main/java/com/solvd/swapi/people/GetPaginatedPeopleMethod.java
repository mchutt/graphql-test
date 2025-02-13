package com.solvd.swapi.people;

import com.solvd.swapi.common.AbstractPaginatedAPIMethod;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import io.restassured.response.Response;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class GetPaginatedPeopleMethod extends AbstractPaginatedAPIMethod {


    public void setPaginationDirection(boolean isForward){
        if (isForward){
            setRequestTemplate("api/swapi/people/getPaginatedPeople/rq-forward.json");
        }else {
            setRequestTemplate("api/swapi/people/getPaginatedPeople/rq-backward.json");
        }
    }

    @Override
    public String getEndCursor(Response response) {
        return response.jsonPath().getString("data.allPeople.pageInfo.endCursor");
    }

    @Override
    public String getStartCursor(Response response) {
        return response.jsonPath().getString("data.allPeople.pageInfo.startCursor");
    }

    @Override
    public boolean hasNextPage(Response response) {
        return response.jsonPath().getBoolean("data.allPeople.pageInfo.hasNextPage");
    }

    @Override
    public boolean hasPreviousPage(Response response) {
        return response.jsonPath().getBoolean("data.allPeople.pageInfo.hasPreviousPage");
    }
}
