package com.solvd.gorest;

import com.solvd.gorest.models.Post;
import com.solvd.gorest.posts.*;
import com.solvd.gorest.users.CreateUserMethod;
import com.zebrunner.carina.core.IAbstractTest;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.solvd.gorest.UsersTest.deleteUser;
import static com.solvd.gorest.utils.APIConstants.TOKEN;

public class PostsTest implements IAbstractTest {
    private static final String RESOURCES_PATH = "api/graphql/posts/";
    private static String USER_ID;
    private static Post post;


    @BeforeClass
    public void setupUser() {
        CreateUserMethod userApi = new CreateUserMethod();
        userApi.setHeader("Authorization", "Bearer " + TOKEN);
        Response response = userApi.callAPIExpectSuccess();
        USER_ID = response.jsonPath().getString("data.createUser.user.id");
    }


    @DataProvider(name = "createPostData")
    public Object[][] createPostData() {
        return new Object[][]{
                {true},
                {false},
        };
    }

    @Test(dataProvider = "createPostData")
    public void createPostTest(boolean isAuthorized) {
        CreatePostMethod api = new CreatePostMethod();
        api.getProperties().setProperty("userId", USER_ID);
        if (isAuthorized) {
            api.setHeader("Authorization", "Bearer " + TOKEN);
            Response response = api.callAPIExpectSuccess();
            post = response.jsonPath().getObject("data.createPost.post", Post.class);
            api.validateResponse();
        } else {
            api.setResponseTemplate(RESOURCES_PATH + "createPost/rs-non-authorized.json");
            api.callAPIExpectSuccess();
            api.validateResponse();
        }
    }

    @Test(dependsOnMethods = {"createPostTest"})
    public void getPostByIdTest() {
        GetPostByIdMethod api = new GetPostByIdMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("id", String.valueOf(post.getId()));
        api.getProperties().setProperty("body", post.getBody());
        api.getProperties().setProperty("title", post.getTitle());
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test(dependsOnMethods = {"getPostByIdTest", "createPostTest"})
    public void updatePostTest() {
        UpdatePostMethod api = new UpdatePostMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("id", String.valueOf(post.getId()));
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test(dependsOnMethods = {"getPostByIdTest", "createPostTest", "updatePostTest"})
    public void deletePostTest() {
        DeletePostMethod api = new DeletePostMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("id", String.valueOf(post.getId()));
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void getAllPostsTest() {
        GetAllPostsMethod api = new GetAllPostsMethod();
        api.callAPIExpectSuccess();
        api.validateResponseAgainstSchema(RESOURCES_PATH + "getAll/rs.schema");
    }

    @AfterClass
    public void afterClass() {
        if (USER_ID != null){
            deleteUser(USER_ID);
        }
    }

}
