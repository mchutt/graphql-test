package com.solvd.gorest;

import com.solvd.gorest.models.Todo;
import com.solvd.gorest.todos.*;
import com.zebrunner.carina.core.IAbstractTest;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Properties;

import static com.solvd.gorest.utils.APIConstants.TOKEN;

public class TodosTest implements IAbstractTest {

    private static final String USER_ID = "7678481";

    @Test
    public void createTodoTest() {
        createTodo(USER_ID);
    }

    @Test
    public void getTodoByIdTest() {
        Todo todo = createTodo(USER_ID);

        GetTodoByIdMethod api = new GetTodoByIdMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("id", String.valueOf(todo.getId()));
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @DataProvider(name = "updateTodoData")
    public Object[][] updateTodoData() {
        Properties p1 = new Properties();
        p1.setProperty("dueOn", "2025-02-05T16:00:00-03:00");
        Properties p2 = new Properties();
        p2.setProperty("status", "completed");
        Properties p3 = new Properties();
        p3.setProperty("title", "updated title");
        return new Object[][]{
                {p1},
                {p2},
                {p3}
        };
    }

    @Test(dataProvider = "updateTodoData")
    public void updateTodoTest(Properties props) {
        Todo todo = createTodo(USER_ID);

        UpdateTodoMethod api = new UpdateTodoMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        props.setProperty("id", String.valueOf(todo.getId()));
        api.setProperties(props);
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void deleteTodoTest() {
        Todo todo = createTodo(USER_ID);

        DeleteTodoMethod api = new DeleteTodoMethod();
        api.setHeader("Authorization", "Bearer " + TOKEN);
        api.getProperties().setProperty("id", String.valueOf(todo.getId()));
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void getAllTodosTest() {
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
