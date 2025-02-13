package com.solvd.swapi;

import com.solvd.swapi.people.*;
import com.zebrunner.carina.api.APIMethodPoller;
import com.zebrunner.carina.core.IAbstractTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.solvd.swapi.people.GetAllPeopleMethod.getPersonList;
import static com.solvd.swapi.people.GetPersonByIdMethod.getPerson;


public class PeopleTest implements IAbstractTest {

    public static final String LUKE_ID = "cGVvcGxlOjE=";

    @Test
    public void testGetAllPeople() {
        GetAllPeopleMethod api = new GetAllPeopleMethod();
        Response response = api.callAPIExpectSuccess();
        //verify that name and birthYear values are not null
        List<Person> people = getPersonList(response);
        SoftAssert softAssert = new SoftAssert();
        people.forEach(person -> {
            softAssert.assertNotNull(person.getName());
            softAssert.assertNotNull(person.getBirthYear());
        });
        softAssert.assertAll();
    }

    @Test
    public void testGetPersonById() {
        GetPersonByIdMethod api = new GetPersonByIdMethod();
        api.setId(LUKE_ID);
        Response response = api.callAPIExpectSuccess();
        Person person = getPerson(response);
        Assert.assertEquals(person.getName(), "Luke Skywalker", "Person name does not match");
    }

    @Test
    public void testGetInvalidField(){
        GetInvalidFieldMethod api = new GetInvalidFieldMethod();
        Response response = api.callAPIExpectSuccess();
        String errorMessage = api.getErrorMessage(response);
        Assert.assertNotNull(errorMessage);
    }

    @DataProvider(name = "testPaginationData")
    public Object[][] testPaginationData(){
        return new Object[][] {
                {true, 40, 3},
                {false, 40, 3}
        };
    }

    @Test(dataProvider = "testPaginationData")
    public void testFilmPagination(boolean forward, int pageSize, int expectedPages) {
        GetPaginatedPeopleMethod api = new GetPaginatedPeopleMethod();
        AtomicInteger pageCount = new AtomicInteger(0);

        if (forward) {
            api.setFirst(pageSize);
            api.setPaginationDirection(true);
            api.setAfter("");
        } else {
            api.setPaginationDirection(false);
            api.setLast(pageSize);
            api.setBefore("");
        }

        api.callAPIWithRetry()
                .peek(response -> {
                    pageCount.getAndIncrement();
                    if (forward) {
                        api.setAfter(api.getEndCursor(response));
                    } else {
                        api.setBefore(api.getStartCursor(response));
                    }
                })
                .until(response -> forward ? !api.hasNextPage(response) : !api.hasPreviousPage(response))
                .withLogStrategy(APIMethodPoller.LogStrategy.ALL)
                .stopAfter(30, ChronoUnit.SECONDS)
                .execute();

        Assert.assertEquals(pageCount.get(), expectedPages, "Page count does not match expected");
        api.validateResponseAgainstSchema("api/swapi/people/getPaginatedPeople/rs.schema");
    }

}
