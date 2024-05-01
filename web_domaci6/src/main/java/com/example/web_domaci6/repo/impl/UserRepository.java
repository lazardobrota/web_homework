package com.example.web_domaci6.repo.impl;

import com.example.web_domaci6.entities.User;
import com.example.web_domaci6.repo.IUserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserRepository implements IUserRepository {

    private static final List<User> users = new CopyOnWriteArrayList<>(Arrays.asList(
            new User("admin", "admin"),
            new User("lazar", "lazar")
    ));

    @Override
    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }
}
