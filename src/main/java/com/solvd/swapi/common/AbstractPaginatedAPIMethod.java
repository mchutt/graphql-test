package com.solvd.swapi.common;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import io.restassured.response.Response;

public abstract class AbstractPaginatedAPIMethod extends AbstractApiMethodV2 {
    //Request helpers
    public void setFirst(int first) {
        getProperties().setProperty("first", String.valueOf(first));
    }

    public void setLast(int last) {
        getProperties().setProperty("last", String.valueOf(last));
    }

    public void setAfter(String after) {
        getProperties().setProperty("after", after);
    }

    public void setBefore(String before) {
        getProperties().setProperty("before", before);
    }


    // Response helpers
    public abstract String getEndCursor(Response response);

    public abstract String getStartCursor(Response response);

    public abstract boolean hasNextPage(Response response);

    public abstract boolean hasPreviousPage(Response response);
}
