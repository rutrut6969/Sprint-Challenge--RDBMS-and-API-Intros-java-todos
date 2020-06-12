package com.lambdaschool.todo.services;

import com.lambdaschool.todo.models.Todo;
import com.lambdaschool.todo.models.User;
import com.lambdaschool.todo.reposirtories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userrepos;

    @Autowired
    private TodoService todoService;

    @Override
    public List<User> findAll() {
        // Sets up new array list to contain the users
        List<User> list = new ArrayList<>();

        // Finds each user using the repository, and adds it to the list
        userrepos.findAll().iterator().forEachRemaining(list::add);

        // returns the list
        return list;
    }

    @Override
    public User findUserById(long id) throws EntityNotFoundException {
        User user = userrepos.findById(id).orElseThrow(() -> new EntityNotFoundException("User with the ID: " + id + " was not found"));
        return user;
    }

    @Transactional
    @Override
    public void delete(long id) {
        // Checks to see if the user exists
        userrepos.findById(id).orElseThrow(() -> new EntityNotFoundException("User with the ID: " + id + " was not found"));

        // Deletes existing user.
        userrepos.deleteById(id);
    }

    @Override
    public User addUser(User user) {
        User newUser = new User();

        if(user.getUserid() != 0){
            User oldUser = userrepos.findById(user.getUserid()).orElseThrow(() -> new EntityNotFoundException("User with the ID: " + user.getUserid() + " was not found"));

            for(Todo ut : oldUser.getTodos()){
                deleteUserTodo(ut.getUser().getUserid(), ut.getTodoid())
            }
        }

        return null;
    }

    @Override
    public void deleteUserTodo(long userid, long todoid) {
            userrepos.findById(userid).orElseThrow(() -> new EntityNotFoundException("User with the ID: " + userid + " was not found"));
            todoService.findTodoById(todoid);
    }
}
