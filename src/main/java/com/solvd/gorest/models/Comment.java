package com.solvd.gorest.models;

import java.util.Objects;

public class Comment {
    private int id;
    private String name;
    private String body;
    private String email;
    private int postId;

    public Comment(int id, String name, String email, int postId, String body) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.postId = postId;
        this.body = body;
    }

    public Comment() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id && postId == comment.postId && Objects.equals(name, comment.name) && Objects.equals(body, comment.body) && Objects.equals(email, comment.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, body, email, postId);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                ", email='" + email + '\'' +
                ", postId=" + postId +
                '}';
    }
}
