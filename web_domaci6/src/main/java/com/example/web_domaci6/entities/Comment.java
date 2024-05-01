package com.example.web_domaci6.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Comment {

    private int id;

    @NotNull(message = "PostId field can't be null")
    private int postId;

    @NotNull(message = "Name field can't be null")
    @NotEmpty(message = "Name field can't be empty")
    private String name;

    @NotNull(message = "Name field can't be null")
    private String comment;

    public Comment() {
    }

    public Comment(int id, int postId, String name, String comment) {
        this.id = id;
        this.postId = postId;
        this.name = name;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }
}
