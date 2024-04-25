package com.example.web_domaci5.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class Post {
    private int id;

    @NotNull(message = "Title field can't be null")
    @NotEmpty(message = "Title field can't be empty")
    private String title;

    private LocalDate dateOfPublish;

    @NotNull(message = "Author field can't be null")
    @NotEmpty(message = "Author field can't be empty")
    private String author;

    @NotNull(message = "Content field can't be null")
    @NotEmpty(message = "Content field can't be empty")
    private String content;

    private List<Comment> comments;


    //Json is dumb and it needs default constructor to function
    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDateOfPublish() {
        return dateOfPublish;
    }

    public void setDateOfPublish(LocalDate dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
