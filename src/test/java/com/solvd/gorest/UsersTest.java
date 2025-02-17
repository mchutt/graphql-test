package com.solvd.gorest;

import com.solvd.gorest.dtos.UserDTO;
import com.solvd.gorest.models.User;
import com.solvd.gorest.services.ApiService;
import com.solvd.gorest.users.*;
import com.zebrunner.carina.core.IAbstractTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.solvd.gorest.users.CreateUserMethod.*;
import static com.solvd.gorest.utils.APIConstants.RESOURCES_PATH;

public class UsersTest implements IAbstractTest {
    private static ApiService apiService;

    @BeforeClass
    public void setApiService() {
        apiService = new ApiService();
    }

    @Test
    public void testCreateUser() {
        apiService.createUser();
    }

    @Test
    public void testGetUserById() {
        User user = apiService.createUser();

        GetUserByIdMethod api = new GetUserByIdMethod();
        api.getProperties().setProperty("id", user.getId());
        api.getProperties().setProperty("email", user.getEmail());
        api.getProperties().setProperty("name", user.getName());
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test(description = "negative")
    public void testGetNonExistentUserById() {
        apiService.validateThatNonExistentResourceReturnsNotFound(new GetUserByIdMethod());
    }

    @Test
    public void testUpdateUser() {
        User user = apiService.createUser();

        UpdateUserMethod api = new UpdateUserMethod();
        api.getProperties().setProperty("id", user.getId());
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test(description = "negative")
    public void testUpdateNonExistentUser() {
        apiService.validateThatNonExistentResourceReturnsNotFound(new UpdateUserMethod());
    }

    @DataProvider(name = "deleteUserData")
    public Object[][] deleteUserData() {
        User user = apiService.createUser();

        return new Object[][]{
                {user.getId(), "users/deleteUser/rs.json"},
                {"1", "users/deleteUser/rs-nonexistent.json"},
        };
    }

    @Test(dataProvider = "deleteUserData")
    public void testDeleteUser(String userId, String responseTemplatePath) {
        apiService.deleteUser(userId, RESOURCES_PATH + responseTemplatePath);
    }

    @Test
    public void testGetAllUsers() {
        GetAllUsersMethod api = new GetAllUsersMethod();
        api.callAPIExpectSuccess();
        api.validateResponseAgainstSchema(RESOURCES_PATH + "users/getAll/rs.schema");
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
}
