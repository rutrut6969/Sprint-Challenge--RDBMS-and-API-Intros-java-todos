package com.lambdaschool.todo.services;

import com.lambdaschool.todo.models.Todo;
import com.lambdaschool.todo.models.User;
import com.lambdaschool.todo.views.JustTheCount;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.util.List;

public interface UserService {

    // Find all Users
    List<User> findAll();

    // Find User by ID
    User findUserById(long id);

    // Delete User Based on ID
    void delete(long id);


    // Add a user
    User save(User user);

    // Get User Todos
    List<JustTheCount> getUserTodoCount();

void deleteUserTodo(long userid, long todoid);
void addUserTodo(long userid, long todoid);
}
