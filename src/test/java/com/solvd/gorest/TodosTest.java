package com.solvd.gorest;

import com.solvd.gorest.models.Todo;
import com.solvd.gorest.services.ApiService;
import com.solvd.gorest.todos.*;
import com.zebrunner.carina.core.IAbstractTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Properties;

public class TodosTest implements IAbstractTest {
    private static ApiService apiService;

    @BeforeClass
    public void setApiService() {
        apiService = new ApiService();
    }

    @Test
    public void testCreateTodo() {
        apiService.createTodo();
    }

    @Test
    public void testGetTodoById() {
        Todo todo = apiService.createTodo();

        GetTodoByIdMethod api = new GetTodoByIdMethod();
        api.getProperties().setProperty("id", String.valueOf(todo.getId()));
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void testGetNonExistentTodoById() {
        apiService.validateThatNonExistentResourceReturnsNotFound(new GetTodoByIdMethod());
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
        Todo todo = apiService.createTodo();

        UpdateTodoMethod api = new UpdateTodoMethod();
        props.setProperty("id", String.valueOf(todo.getId()));
        props.setProperty("userId", String.valueOf(todo.getUserId()));
        api.setProperties(props);
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void testDeleteTodo() {
        Todo todo = apiService.createTodo();

        DeleteTodoMethod api = new DeleteTodoMethod();
        api.setIdProperty(String.valueOf(todo.getId()));
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void testGetAllTodos() {
        GetAllTodosMethod api = new GetAllTodosMethod();
        api.callAPIExpectSuccess();
        api.validateResponseAgainstSchema("api/graphql/todos/getAll/rs.schema");
    }
}
