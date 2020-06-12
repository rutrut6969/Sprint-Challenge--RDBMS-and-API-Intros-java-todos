package com.lambdaschool.todo.controllers;


import com.lambdaschool.todo.models.User;
import com.lambdaschool.todo.reposirtories.UserRepo;
import com.lambdaschool.todo.services.UserService;
import com.lambdaschool.todo.views.JustTheCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    // Return all Users
    @GetMapping(value = "/users", produces = {"application/json"})
    public ResponseEntity<?> listAllUsers(){
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers, HttpStatus.OK);
    }

    // Get Users by ID:
    @GetMapping(value = "/user/{id}", produces = {"application/json"})
    public ResponseEntity<?> findUserById(@PathVariable long id){
        User foundUser = userService.findUserById(id);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    // Creating A User:
    @PostMapping(value = "/user", consumes = {"application/json"})
    public ResponseEntity<?> addUser(@Validated @RequestBody User newuser){
        newuser.setUserid(0);
        newuser = userService.save(newuser);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/userid/{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable long userid){
        userService.delete(userid);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(value = "/users/todos")
    public ResponseEntity<?> getTodoCount(){
       List<JustTheCount> userCount = userService.getUserTodoCount();

       return new ResponseEntity<>(userCount, HttpStatus.OK);
    }


}
