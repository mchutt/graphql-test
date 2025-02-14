package com.solvd.swapi;

import com.solvd.swapi.models.Person;
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


public class PeopleTest implements IAbstractTest {

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

    @DataProvider(name = "personIdDataProvider", parallel = true)
    public Object[][] personIdDataProvider(){
        return new Object[][]{
                {"cGVvcGxlOjE=", "Luke Skywalker", true},
                {"cGVvcGxlOjM=", "R2-D2", true},
                {"", "", false}
        };
    }

    @Test(dataProvider = "personIdDataProvider")
    public void testGetPersonById(String personId, String personName, boolean positive) {
        GetPersonByIdMethod api = new GetPersonByIdMethod();
        api.setId(personId);
        Response response = api.callAPIExpectSuccess();
        Person person = api.getPerson(response);
        if (positive) {
            Assert.assertEquals(person.getName(), personName, "Person name does not match");
        }else {
            Assert.assertNotNull(api.getErrorMessage(response));
            Assert.assertNull(person, "Person should be null");
        }
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
            api.setRequestTemplateBasedOnPaginationDirection(true);
            api.setAfter("");
        } else {
            api.setRequestTemplateBasedOnPaginationDirection(false);
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
