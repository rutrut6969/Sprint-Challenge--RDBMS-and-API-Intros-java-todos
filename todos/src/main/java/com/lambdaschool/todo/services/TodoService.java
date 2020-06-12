package com.lambdaschool.todo.services;

import com.lambdaschool.todo.models.Todo;
import com.lambdaschool.todo.models.User;

import java.util.List;

public interface TodoService {

    // Find all Todos
    List<Todo> findAll();

    // Find Todo by ID
    Todo findTodoById(long id);

    // Add a user
    Todo save(Todo todo);
}
