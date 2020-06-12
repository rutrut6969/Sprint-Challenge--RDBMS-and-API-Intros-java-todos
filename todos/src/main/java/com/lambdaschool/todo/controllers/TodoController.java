package com.lambdaschool.todo.controllers;


import com.lambdaschool.todo.models.Todo;
import com.lambdaschool.todo.services.TodoService;
import com.lambdaschool.todo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    TodoService todoService;

    @Autowired
    UserService userService;

    @PatchMapping(value = "/todo/{todoid}")
    public ResponseEntity<?> completeTodo(@PathVariable long todoid){

        boolean isComp = todoService.findTodoById(todoid).isCompleted();
        if(isComp != true){
            todoService.findTodoById(todoid).setCompleted(true);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(todoService.findTodoById(todoid), HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping(value = "/user/{userid}")
    public ResponseEntity<?> addTodoToUser(@PathVariable long userid, @RequestBody Todo todo){
        userService.findUserById(userid).addTodo(todo);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }
}
