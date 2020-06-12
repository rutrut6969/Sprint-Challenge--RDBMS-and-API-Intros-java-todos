package com.lambdaschool.todo.services;

import com.lambdaschool.todo.models.Todo;
import com.lambdaschool.todo.models.User;
import com.lambdaschool.todo.reposirtories.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TodoServiceImpl implements TodoService {

    @Autowired
    TodoRepo todorepos;


    @Override
    public List<Todo> findAll() {
        List<Todo> todos = new ArrayList<>();
        todorepos.findAll().iterator().forEachRemaining(todos::add);
        return todos;
    }

    @Override
    public Todo findTodoById(long id) {
        return todorepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo " + id + " Not found"));
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Todo addTodo(Todo todo) {
        return null;
    }
}
