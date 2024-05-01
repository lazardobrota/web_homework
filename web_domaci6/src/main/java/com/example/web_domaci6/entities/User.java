package com.example.web_domaci6.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class User {

    @NotNull(message = "Username field can't be null")
    @NotEmpty(message = "Username field can't be empty")
    private String username;

    @NotNull(message = "Password field can't be null")
    @NotEmpty(message = "Password field can't be empty")
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
