package com.solvd.gorest;

import com.solvd.gorest.dtos.UserDTO;
import com.solvd.gorest.models.User;
import com.solvd.gorest.users.*;
import com.zebrunner.carina.core.IAbstractTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.solvd.gorest.users.CreateUserMethod.*;

public class UsersTest implements IAbstractTest {

    private static final String RESOURCES_PATH = "api/graphql/users/";

    @Test
    public void testCreateUser() {
        createUser();
    }

    @Test
    public void testGetUserById() {
        User user = createUser();

        GetUserByIdMethod api = new GetUserByIdMethod();
        api.getProperties().setProperty("id", user.getId());
        api.getProperties().setProperty("email", user.getEmail());
        api.getProperties().setProperty("name", user.getName());
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void testUpdateUser() {
        User user = createUser();

        UpdateUserMethod api = new UpdateUserMethod();
        api.getProperties().setProperty("id", user.getId());
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @DataProvider(name = "deleteUserData")
    public Object[][] deleteUserData() {
        User user = createUser();

        return new Object[][]{
                {user.getId(), "deleteUser/rs.json"},
                {"1", "deleteUser/rs-nonexistent.json"},
        };
    }

    @Test(dataProvider = "deleteUserData")
    public void testDeleteUser(String userId, String responseTemplatePath) {
        deleteUser(userId, responseTemplatePath);
    }

    @Test
    public void testGetAllUsers() {
        GetAllUsersMethod api = new GetAllUsersMethod();
        api.callAPIExpectSuccess();
        api.validateResponseAgainstSchema(RESOURCES_PATH + "getAll/rs.schema");
    }

    @DataProvider(name = "invalidUserData")
    public Object[][] invalidUserData() {
        return new Object[][]{
                {new UserDTO("invalid-email", "male", "John Doe", "active"), "is invalid", "email"},
                {new UserDTO("valid@mail.com", "", "John Doe", "active"), "can't be blank", "gender"},
                {new UserDTO("valid@mail.com", "male", "", "active"), "can't be blank", "name"},
        };
    }

    @Test(dataProvider = "invalidUserData")
    public void testCreateUserWithInvalidData(UserDTO u, String expectedMessage, String expectedFieldName) {
        CreateUserMethod api = new CreateUserMethod();
        api.addUserProperties(u);
        String response = api.callAPIExpectSuccess().asString();
        Assert.assertEquals(getFieldName(response), expectedFieldName, "FieldName does not match expected.");
        Assert.assertEquals(getMessage(response), expectedMessage, "Error message does not match expected.");
    }

    //helper methods
    private User createUser() {
        CreateUserMethod api = new CreateUserMethod();
        Response response = api.callAPIExpectSuccess();
        api.validateResponseAgainstSchema(RESOURCES_PATH + "createUser/rs.schema");
        return response.jsonPath().getObject("data.createUser.user", User.class);
    }

    public static void deleteUser(String userId) {
        deleteUser(userId, null);
    }

    public static void deleteUser(String userId, String responseTemplatePath) {
        DeleteUserMethod api = new DeleteUserMethod();
        api.getProperties().setProperty("id", userId);
        if (responseTemplatePath != null)
            api.setResponseTemplate(RESOURCES_PATH + responseTemplatePath);
        api.callAPIExpectSuccess();
        api.validateResponse();
    }
}
