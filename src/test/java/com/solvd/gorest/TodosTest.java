package com.solvd.gorest;

import com.solvd.gorest.models.Todo;
import com.solvd.gorest.todos.*;
import com.zebrunner.carina.core.IAbstractTest;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Properties;

import static com.solvd.gorest.utils.APIConstants.USER_ID;

public class TodosTest implements IAbstractTest {

    @Test
    public void testCreateTodo() {
        createTodo();
    }

    @Test
    public void testGetTodoById() {
        Todo todo = createTodo();

        GetTodoByIdMethod api = new GetTodoByIdMethod();
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
    public void testUpdateTodo(Properties props) {
        Todo todo = createTodo();

        UpdateTodoMethod api = new UpdateTodoMethod();
        props.setProperty("id", String.valueOf(todo.getId()));
        api.setProperties(props);
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void testDeleteTodo() {
        Todo todo = createTodo();

        DeleteTodoMethod api = new DeleteTodoMethod();
        api.getProperties().setProperty("id", String.valueOf(todo.getId()));
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void testGetAllTodos() {
        GetAllTodosMethod api = new GetAllTodosMethod();
        api.callAPIExpectSuccess();
        api.validateResponseAgainstSchema("api/graphql/todos/getAll/rs.schema");
    }

    //helper methods
    private Todo createTodo() {
        CreateTodoMethod api = new CreateTodoMethod();
        api.getProperties().setProperty("userId", USER_ID);
        Response response = api.callAPIExpectSuccess();
        api.validateResponse();
        return response.jsonPath().getObject("data.createTodo.todo", Todo.class);

    }
}
