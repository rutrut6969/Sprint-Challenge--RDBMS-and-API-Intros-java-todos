package com.lambdaschool.todo.services;

import com.lambdaschool.todo.models.Todo;
import com.lambdaschool.todo.models.User;

import java.util.List;

public interface UserService {

    // Find all Users
    List<User> findAll();

    // Find User by ID
    User findUserById(long id);

    // Delete User Based on ID
    void delete(long id);


    // Add a user
    User addUser(User user);

    // Get User Todos


void deleteUserTodo(long userid, long todoid);
}
