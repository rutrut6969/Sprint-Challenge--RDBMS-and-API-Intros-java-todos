package com.lambdaschool.todo.services;

import com.lambdaschool.todo.models.Todo;
import com.lambdaschool.todo.models.User;
import com.lambdaschool.todo.reposirtories.TodoRepo;
import com.lambdaschool.todo.reposirtories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "todoService")
public class TodoServiceImpl implements TodoService {

    @Autowired
    TodoRepo todorepos;

    @Autowired
    UserRepo userrepos;

    @Autowired
    UserService userService;


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
    public Todo save(long userid, Todo todo) {

        User currentUser = userService.findUserById(userid);
        Todo savedTodo = new Todo(currentUser, todo.getDescription());
        currentUser.addTodo(savedTodo);
        todorepos.save(savedTodo);

        return savedTodo;
    }

    @Override
    public void complete(Todo todo){
        boolean isComp = todo.isCompleted();
        if (!isComp){
            todo.setCompleted(true);
        }
    }

}
