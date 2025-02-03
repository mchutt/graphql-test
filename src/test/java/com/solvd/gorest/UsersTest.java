package com.solvd.gorest;

import com.solvd.gorest.models.User;
import com.solvd.gorest.users.*;
import com.zebrunner.carina.core.IAbstractTest;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.solvd.gorest.utils.APIConstants.TOKEN;

public class UsersTest implements IAbstractTest {

    public static final String RESOURCES_PATH = "api/graphql/users/";
    public static User USER = null;

    @DataProvider(name = "deleteUserData")
    public Object[][] deleteUserData() {
        return new Object[][]{
                {USER.getId(), true},
                {"1", false},
        };
    }

    @Test
    public void createUserTest() {
        CreateUserMethod api = new CreateUserMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        Response response = api.callAPIExpectSuccess();
        USER = response.jsonPath().getObject("data.createUser.user", User.class);
        api.validateResponseAgainstSchema(RESOURCES_PATH + "createUser/rs.schema");
    }

    @Test(dependsOnMethods = {"createUserTest"})
    public void getUserByIdTest() {
        GetUserByIdMethod api = new GetUserByIdMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("id", USER.getId());
        api.getProperties().setProperty("email", USER.getEmail());
        api.getProperties().setProperty("name", USER.getName());
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void getAllUsersTest() {
        GetAllUsersMethod api = new GetAllUsersMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.callAPIExpectSuccess();
        api.validateResponseAgainstSchema(RESOURCES_PATH + "getAll/rs.schema");
    }

    @Test(dependsOnMethods = {"getUserByIdTest", "createUserTest"})
    public void updateUserTest() {
        UpdateUserMethod api = new UpdateUserMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("id", USER.getId());
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test(dependsOnMethods = {"createUserTest", "getUserByIdTest"}, dataProvider = "deleteUserData")
    public void deleteUserTest(String userId, boolean isPositive) {
        DeleteUserMethod api = new DeleteUserMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("id", userId);
        if (isPositive) {
            api.setResponseTemplate(RESOURCES_PATH + "deleteUser/rs.json");
        } else {
            api.setResponseTemplate(RESOURCES_PATH + "deleteUser/rs-nonexistent.json");
        }
        api.callAPIExpectSuccess();
        api.validateResponse();
    }


}
