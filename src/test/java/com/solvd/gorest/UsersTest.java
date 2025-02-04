package com.solvd.gorest;

import com.solvd.gorest.models.User;
import com.solvd.gorest.users.*;
import com.zebrunner.carina.core.IAbstractTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.solvd.gorest.utils.APIConstants.TOKEN;

public class UsersTest implements IAbstractTest {

    private static final String RESOURCES_PATH = "api/graphql/users/";
    private static User USER;

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

    @Test(dependsOnMethods = {"getUserByIdTest", "createUserTest"})
    public void updateUserTest() {
        UpdateUserMethod api = new UpdateUserMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("id", USER.getId());
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @DataProvider(name = "deleteUserData")
    public Object[][] deleteUserData() {
        return new Object[][]{
                {USER.getId(), "deleteUser/rs.json"},
                {"1", "deleteUser/rs-nonexistent.json"},
        };
    }

    @Test(dependsOnMethods = {"createUserTest", "getUserByIdTest"}, dataProvider = "deleteUserData")
    public void deleteUserTest(String userId, String responseTemplatePath) {
        deleteUser(userId, responseTemplatePath);
    }

    @Test
    public void getAllUsersTest() {
        GetAllUsersMethod api = new GetAllUsersMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.callAPIExpectSuccess();
        api.validateResponseAgainstSchema(RESOURCES_PATH + "getAll/rs.schema");
    }

    @DataProvider(name = "invalidUserData")
    public Object[][] invalidUserData() {
        return new Object[][]{
                {"invalid-email", "male", "John Doe", "active", "is invalid", "email"},
                {"valid@mail.com", "", "John Doe", "active", "can't be blank", "gender"},
                {"valid@mail.com", "male", "", "active", "can't be blank", "name"},
        };
    }

    @Test(dataProvider = "invalidUserData")
    public void createUserWithInvalidDataTest(String email, String gender, String name, String status, String expectedMessage, String expectedFieldName) {
        CreateUserMethod api = new CreateUserMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("email", email);
        api.getProperties().setProperty("gender", gender);
        api.getProperties().setProperty("name", name);
        api.getProperties().setProperty("status", status);

        Response response = api.callAPIExpectSuccess();
        String fieldName = response.jsonPath().get("errors[0].extensions.result[0].fieldName");
        String message = response.jsonPath().get("errors[0].extensions.result[0].messages[0]");
        Assert.assertEquals(fieldName, expectedFieldName, "FieldName does not match expected.");
        Assert.assertEquals(message, expectedMessage, "Error message does not match expected.");
    }


    //helper methods
    public static void deleteUser(String userId) {
        deleteUser(userId, null);
    }

    public static void deleteUser(String userId, String responseTemplatePath) {
        DeleteUserMethod api = new DeleteUserMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("id", userId);
        if (responseTemplatePath != null)
            api.setResponseTemplate(RESOURCES_PATH + responseTemplatePath);
        api.callAPIExpectSuccess();
        api.validateResponse();
    }
}
