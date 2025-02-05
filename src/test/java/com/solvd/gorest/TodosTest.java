package com.solvd.gorest;

import com.solvd.gorest.models.Todo;
import com.solvd.gorest.todos.*;
import com.zebrunner.carina.core.IAbstractTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.solvd.gorest.utils.APIConstants.TOKEN;

public class TodosTest implements IAbstractTest {

    private static final String USER_ID = "7678481";

    @Test
    public void createTodoTest(){
        createTodo(USER_ID);
    }

    @Test
    public void getTodoByIdTest(){
        Todo todo = createTodo(USER_ID);

        GetTodoByIdMethod api = new GetTodoByIdMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("id", String.valueOf(todo.getId()));
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void updateTodoTest(){
        Todo todo = createTodo(USER_ID);

        UpdateTodoMethod api = new UpdateTodoMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("id", String.valueOf(todo.getId()));
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void deleteTodoTest(){
        Todo todo = createTodo(USER_ID);

        DeleteTodoMethod api = new DeleteTodoMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("id", String.valueOf(todo.getId()));
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void getAllTodosTest(){
        GetAllTodosMethod api = new GetAllTodosMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.callAPIExpectSuccess();
        api.validateResponseAgainstSchema("api/graphql/todos/getAll/rs.schema");
    }



    //helper methods
    private static Todo createTodo(String userId) {
        CreateTodoMethod api = new CreateTodoMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("userId", userId);
        Response response = api.callAPIExpectSuccess();
        api.validateResponse();
        return response.jsonPath().getObject("data.createTodo.todo", Todo.class);

    }
}
