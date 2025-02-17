package com.solvd.gorest;

import com.solvd.gorest.comments.CreateCommentMethod;
import com.solvd.gorest.comments.DeleteCommentMethod;
import com.solvd.gorest.comments.GetCommentByIdMethod;
import com.solvd.gorest.comments.UpdateCommentMethod;
import com.solvd.gorest.models.Comment;
import com.solvd.gorest.models.Post;
import com.solvd.gorest.services.ApiService;
import com.zebrunner.carina.core.AbstractTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.solvd.gorest.utils.APIConstants.NOT_AUTHENTICATED_MESSAGE;
import static com.solvd.gorest.utils.APIConstants.RESOURCES_PATH;

public class CommentsTest extends AbstractTest {
    private static ApiService apiService;

    @BeforeClass
    public void setApiService() {
        apiService = new ApiService();
    }

    @DataProvider
    public Object[][] createUserData() {
        return new Object[][]{
                {true},
                {false}
        };
    }

    @Test(dataProvider = "createUserData")
    public void testCreateComment(boolean authorized) {
        Post post = apiService.createPost();

        CreateCommentMethod api = new CreateCommentMethod();
        api.setPostId(post.getId());
        if (!authorized) api.removeAuthorizationHeader();
        Response response = api.callAPIExpectSuccess();
        if (!authorized) {
            String errorMessage = response.jsonPath().getString("errors[0].message");
            Assert.assertEquals(errorMessage, NOT_AUTHENTICATED_MESSAGE);
        } else {
            api.validateResponse();
        }

    }

    @Test
    public void testCreateCommentWithInvalidEmail() {
        Post post = apiService.createPost();

        CreateCommentMethod api = new CreateCommentMethod();
        api.setPostId(post.getId());
        api.setEmail("invalid email");
        api.callAPIExpectSuccess();
        api.setResponseTemplate(RESOURCES_PATH + "comments/createComment/rs-invalid-email.json");
        api.validateResponse();
    }

    @Test
    public void testUpdateComment() {
        Comment comment = apiService.createComment();

        UpdateCommentMethod api = new UpdateCommentMethod();
        api.setId(comment.getId());
        api.setEmail("UPDATED.EMAIL@EMAIL.COM");
        api.setName("UPDATED NAME");
        api.setBody("UPDATED BODY");
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test(description = "negative")
    public void testUpdateCommentWithoutAuthorization() {
        UpdateCommentMethod api = new UpdateCommentMethod();
        api.setId(1);
        apiService.validateAPIWithoutAuthenticationReturnsNotAuthenticatedMessage(api);
    }

    @Test(description = "negative")
    public void testUpdateNonExistentComment() {
        apiService.validateNonexistentResourceReturnsNotFoundMessage(new UpdateCommentMethod());
    }

    @Test
    public void testGetCommentById() {
        Comment comment = apiService.createComment();

        GetCommentByIdMethod api = new GetCommentByIdMethod();
        api.setId(comment.getId());
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test(description = "negative")
    public void testGetNonExistentCommentById() {
        apiService.validateNonexistentResourceReturnsNotFoundMessage(new GetCommentByIdMethod());
    }

    @Test
    public void testDeleteComment() {
        Comment comment = apiService.createComment();

        DeleteCommentMethod api = new DeleteCommentMethod();
        api.setId(comment.getId());
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test(description = "negative")
    public void testDeleteNonExistentCommentWithoutAuthorization() {
        DeleteCommentMethod api = new DeleteCommentMethod();
        api.setId(1);
        apiService.validateAPIWithoutAuthenticationReturnsNotAuthenticatedMessage(api);
    }


    @Test(description = "negative")
    public void testDeleteNonExistentComment() {
        apiService.validateNonexistentResourceReturnsNotFoundMessage(new DeleteCommentMethod());
    }

}
