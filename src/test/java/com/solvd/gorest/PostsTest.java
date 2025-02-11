package com.solvd.gorest;

import com.solvd.gorest.models.Post;
import com.solvd.gorest.models.User;
import com.solvd.gorest.posts.*;
import com.zebrunner.carina.core.IAbstractTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.solvd.gorest.UsersTest.createUser;

public class PostsTest implements IAbstractTest {
    private static final String RESOURCES_PATH = "api/graphql/posts/";

    @DataProvider(name = "createPostData")
    public Object[][] createPostData() {
        return new Object[][]{
                {true},
                {false},
        };
    }

    @Test(dataProvider = "createPostData")
    public void testCreatePost(boolean isAuthorized) {
        User user = createUser();
        CreatePostMethod api = new CreatePostMethod();
        api.getProperties().setProperty("userId", user.getId());
        if (!isAuthorized) {
            api.removeAuthorizationHeader();
            api.setResponseTemplate(RESOURCES_PATH + "createPost/rs-non-authorized.json");
        }
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void testGetPostById() {
        Post post = createPost();

        GetPostByIdMethod api = new GetPostByIdMethod();
        api.getProperties().setProperty("id", String.valueOf(post.getId()));
        api.getProperties().setProperty("body", post.getBody());
        api.getProperties().setProperty("title", post.getTitle());
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void testUpdatePost() {
        Post post = createPost();

        UpdatePostMethod api = new UpdatePostMethod();
        api.getProperties().setProperty("id", String.valueOf(post.getId()));
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void testDeletePost() {
        Post post = createPost();

        DeletePostMethod api = new DeletePostMethod();
        api.getProperties().setProperty("id", String.valueOf(post.getId()));
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void testDeletePostWithOutToken() {
        Post post = createPost();

        DeletePostMethod api = new DeletePostMethod();
        api.getProperties().setProperty("id", String.valueOf(post.getId()));
        api.removeAuthorizationHeader();
        String response = api.callAPIExpectSuccess().asString();
        Assert.assertEquals(DeletePostMethod.getErrorMessage(response), "You need to authenticate your self to perform this action");
    }

    @Test
    public void testGetAllPosts() {
        GetAllPostsMethod api = new GetAllPostsMethod();
        api.callAPIExpectSuccess();
        api.validateResponseAgainstSchema(RESOURCES_PATH + "getAll/rs.schema");
    }

    //helper methods
    private Post createPost() {
        User user = createUser();
        CreatePostMethod api = new CreatePostMethod();
        api.getProperties().setProperty("userId", user.getId());
        Response response = api.callAPIExpectSuccess();
        api.validateResponse();
        return response.jsonPath().getObject("data.createPost.post", Post.class);
    }

}
