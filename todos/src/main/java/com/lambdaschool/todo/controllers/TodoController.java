package com.lambdaschool.todo.controllers;


import com.lambdaschool.todo.models.Todo;
import com.lambdaschool.todo.models.User;
import com.lambdaschool.todo.services.TodoService;
import com.lambdaschool.todo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    TodoService todoService;


    // get all todos
    @GetMapping(value = "/todos", produces = {"application/json"})
    public ResponseEntity<?> findAllTodos(){
        List<Todo> myTodos = todoService.findAll();
        return new ResponseEntity<>(myTodos, HttpStatus.OK);
    }

    @PatchMapping(value = "/todo/{todoid}")
    public ResponseEntity<?> completeTodo(@PathVariable long todoid){
        Todo foundTodo = todoService.findTodoById(todoid);
        todoService.complete(foundTodo);
        return new ResponseEntity<>(foundTodo.isCompleted(), HttpStatus.OK);
    }

//    Works but doesnt add todos to user for some reason
    @PostMapping(value = "/user/{userid}")
    public ResponseEntity<?> addTodoToUser(@PathVariable long userid, @RequestBody Todo todo){
        Todo newTodo = todoService.save(userid, todo);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/todos/todo/{todoid}")
                .buildAndExpand(newTodo.getTodoid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
}
