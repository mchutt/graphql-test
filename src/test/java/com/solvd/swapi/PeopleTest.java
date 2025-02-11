package com.solvd.swapi;

import com.solvd.swapi.people.GetAllPeopleMethod;
import com.solvd.swapi.people.GetInvalidFieldMethod;
import com.solvd.swapi.people.GetPersonByIdMethod;
import com.solvd.swapi.people.Person;
import com.zebrunner.carina.core.IAbstractTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

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



}
