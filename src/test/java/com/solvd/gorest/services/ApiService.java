package com.solvd.gorest.services;

import com.solvd.gorest.AuthorizedApiMethod;
import com.solvd.gorest.comments.CreateCommentMethod;
import com.solvd.gorest.models.Comment;
import com.solvd.gorest.models.Post;
import com.solvd.gorest.models.Todo;
import com.solvd.gorest.models.User;
import com.solvd.gorest.posts.CreatePostMethod;
import com.solvd.gorest.todos.CreateTodoMethod;
import com.solvd.gorest.users.CreateUserMethod;
import com.solvd.gorest.users.DeleteUserMethod;
import com.solvd.gorest.utils.IResponseMethods;
import com.zebrunner.carina.api.AbstractApiMethodV2;
import io.restassured.response.Response;
import org.testng.Assert;

import static com.solvd.gorest.utils.APIConstants.*;

public class ApiService {

    public Post createPost() {
        User user = createUser();
        CreatePostMethod api = new CreatePostMethod();
        api.getProperties().setProperty("userId", user.getId());
        Response response = api.callAPIExpectSuccess();
        api.validateResponse();
        return response.jsonPath().getObject("data.createPost.post", Post.class);
    }


    public Todo createTodo() {
        User user = createUser();
        CreateTodoMethod api = new CreateTodoMethod();
        api.getProperties().setProperty("userId", user.getId());
        Response response = api.callAPIExpectSuccess();
        api.validateResponse();
        return response.jsonPath().getObject("data.createTodo.todo", Todo.class);
    }


    public User createUser() {
        CreateUserMethod api = new CreateUserMethod();
        Response response = api.callAPIExpectSuccess();
        api.validateResponseAgainstSchema(RESOURCES_PATH + "users/createUser/rs.schema");
        return response.jsonPath().getObject("data.createUser.user", User.class);
    }

    public void deleteUser(String userId, String responseTemplatePath) {
        DeleteUserMethod api = new DeleteUserMethod();
        api.getProperties().setProperty("id", userId);
        if (responseTemplatePath != null)
            api.setResponseTemplate(responseTemplatePath);
        api.callAPIExpectSuccess();
        api.validateResponse();
    }


    public Comment createComment() {
        Post post = createPost();

        CreateCommentMethod api = new CreateCommentMethod();
        api.setPostId(post.getId());
        Response response = api.callAPIExpectSuccess();
        api.validateResponse();
        return response.jsonPath().getObject("data.createComment.comment", Comment.class);
    }



    /**
     * Validates that calling the API with a non-existent ID returns a "Resource not found!" message.
     * @param api the API method under test.
     */
    public void validateNonexistentResourceReturnsNotFoundMessage(AbstractApiMethodV2 api) {
        api.getProperties().setProperty("id", "1");
        String response = api.callAPIExpectSuccess().asString();
        String errorMessage = ((IResponseMethods) api).getErrorMessage(response);
        Assert.assertEquals(errorMessage, NOT_FOUND);
    }

    /**
     * Validates that calling the API without an authentication header returns a "You need to authenticate yourself to perform this action" message.
     * @param api the API method under test.
     */
    public void validateAPIWithoutAuthenticationReturnsNotAuthenticatedMessage(AbstractApiMethodV2 api) {
        ((AuthorizedApiMethod) api).removeAuthorizationHeader();
        String response = api.callAPIExpectSuccess().asString();
        String errorMessage = ((IResponseMethods) api).getErrorMessage(response);
        Assert.assertEquals(errorMessage, NOT_AUTHENTICATED_MESSAGE);
    }
}
