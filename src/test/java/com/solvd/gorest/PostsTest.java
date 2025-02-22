package com.solvd.gorest;

import com.solvd.gorest.models.Post;
import com.solvd.gorest.models.User;
import com.solvd.gorest.posts.*;
import com.solvd.gorest.services.ApiService;
import com.zebrunner.carina.core.IAbstractTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.solvd.gorest.utils.APIConstants.RESOURCES_PATH;

public class PostsTest implements IAbstractTest {
    private static ApiService apiService;

    @BeforeClass
    public void setApiService() {
        apiService = new ApiService();
    }

    @DataProvider(name = "createPostData")
    public Object[][] createPostData() {
        return new Object[][]{
                {true},
                {false},
        };
    }

    @Test(dataProvider = "createPostData")
    public void testCreatePost(boolean isAuthorized) {
        User user = apiService.createUser();
        CreatePostMethod api = new CreatePostMethod();
        api.getProperties().setProperty("userId", user.getId());
        if (!isAuthorized) {
            api.removeAuthorizationHeader();
            api.setResponseTemplate(RESOURCES_PATH + "posts/createPost/rs-non-authorized.json");
        }
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void testGetPostById() {
        Post post = apiService.createPost();

        GetPostByIdMethod api = new GetPostByIdMethod();
        api.getProperties().setProperty("id", String.valueOf(post.getId()));
        api.getProperties().setProperty("body", post.getBody());
        api.getProperties().setProperty("title", post.getTitle());
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test(description = "negative")
    public void testGetNonExistentPostById() {
        apiService.validateNonexistentResourceReturnsNotFoundMessage(new GetPostByIdMethod());
    }

    @Test
    public void testUpdatePost() {
        Post post = apiService.createPost();

        UpdatePostMethod api = new UpdatePostMethod();
        api.getProperties().setProperty("id", String.valueOf(post.getId()));
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test(description = "negative")
    public void testUpdatePostWithoutAuthorization() {
        UpdatePostMethod api = new UpdatePostMethod();
        api.getProperties().setProperty("id", "1");
        apiService.validateAPIWithoutAuthenticationReturnsNotAuthenticatedMessage(api);
    }

    @Test(description = "negative")
    public void testUpdateNonExistentPost() {
        apiService.validateNonexistentResourceReturnsNotFoundMessage(new UpdatePostMethod());
    }

    @Test
    public void testDeletePost() {
        Post post = apiService.createPost();

        DeletePostMethod api = new DeletePostMethod();
        api.getProperties().setProperty("id", String.valueOf(post.getId()));
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test(description = "negative")
    public void testDeleteNonExistentPost() {
        apiService.validateNonexistentResourceReturnsNotFoundMessage(new DeletePostMethod());
    }

    @Test(description = "negative")
    public void testDeletePostWithoutAuthorization() {
        DeletePostMethod api = new DeletePostMethod();
        api.getProperties().setProperty("id", "1");
        apiService.validateAPIWithoutAuthenticationReturnsNotAuthenticatedMessage(api);
    }

    @Test
    public void testGetAllPosts() {
        GetAllPostsMethod api = new GetAllPostsMethod();
        api.callAPIExpectSuccess();
        api.validateResponseAgainstSchema(RESOURCES_PATH + "posts/getAll/rs.schema");
    }

}
