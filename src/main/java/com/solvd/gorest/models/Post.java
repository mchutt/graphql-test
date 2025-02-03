package com.solvd.gorest.models;

public class Post {
    private int id;
    private String body;
    private String title;
    private int userId;

    public Post() {
    }

    public Post(int id, String body, String title, int userId) {
        this.id = id;
        this.body = body;
        this.title = title;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    @Override
    public String toString() {
        return "Posts{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                '}';
    }
}
