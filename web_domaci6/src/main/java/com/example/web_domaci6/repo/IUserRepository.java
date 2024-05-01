package com.example.web_domaci6.repo;

import com.example.web_domaci6.entities.User;

public interface IUserRepository {
    public User getUser(String username);
}
