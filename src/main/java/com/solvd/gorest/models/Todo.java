package com.solvd.gorest.models;

import java.time.ZonedDateTime;

public class Todo {
    private int id;
    private String status;
    private String title;
    private int userId;
    private ZonedDateTime dueOn;

    public Todo() {
    }

    public Todo(int id, String status, String title, int userId, ZonedDateTime dueOn) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.userId = userId;
        this.dueOn = dueOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ZonedDateTime getDueOn() {
        return dueOn;
    }

    public void setDueOn(ZonedDateTime dueOn) {
        this.dueOn = dueOn;
    }
}
