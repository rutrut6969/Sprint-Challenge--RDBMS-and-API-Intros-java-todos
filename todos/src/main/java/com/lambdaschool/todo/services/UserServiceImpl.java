package com.lambdaschool.todo.services;

import com.lambdaschool.todo.models.Todo;
import com.lambdaschool.todo.models.User;
import com.lambdaschool.todo.models.UserTodos;
import com.lambdaschool.todo.reposirtories.UserRepo;
import com.lambdaschool.todo.views.JustTheCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
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

    @Autowired
    private UserAuditing userAuditing;

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
    public User save(User user) {
        User newUser = new User();

        if (user.getUserid() != 0) {
            User oldUser = userrepos.findById(user.getUserid()).orElseThrow(() -> new EntityNotFoundException("User with the ID: " + user.getUserid() + " was not found"));

            for (UserTodos ut : oldUser.getTodos()) {
                deleteUserTodo(ut.getUser().getUserid(), ut.getTodo().getTodoid());
            }
            newUser.setUserid(user.getUserid());
        }
        newUser.setUsername(user.getUsername()
                .toLowerCase());
        newUser.setPassword(user.getPassword());
        newUser.setPrimaryemail(user.getPrimaryemail());
        newUser.getTodos().clear();

        if (user.getUserid() == 0) {
            for (UserTodos t : user.getTodos()) {
                Todo newTodo = todoService.findTodoById(t.getTodo().getTodoid());
                newUser.addTodo(newTodo);
            }
        } else {
            for (UserTodos t : user.getTodos()) {
                addUserTodo(t.getUser().getUserid(), t.getTodo().getTodoid());
            }
        }

        return userrepos.save(newUser);
    }


    // Delete Todos by specific User
    @Override
    public void deleteUserTodo(long userid, long todoid) {
        userrepos.findById(userid).orElseThrow(() -> new EntityNotFoundException("User with the ID: " + userid + " was not found"));
        todoService.findTodoById(todoid);

        if (userrepos.checkUserTodosCombo(userid, todoid).getCount() > 0) {
            userrepos.deleteUserTodos(userid, todoid);
        } else {
            throw new EntityNotFoundException("Todo and User Combination Does not exist");
        }
    }

    @Transactional
    @Override
    public void addUserTodo(long userid, long todoid) {
        userrepos.findById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User id " + userid + " not found!"));
        todoService.findTodoById(todoid);

        if (userrepos.checkUserTodosCombo(userid, todoid).getCount() <= 0) {
            userrepos.insertUserTodos(userAuditing.getCurrentAuditor().get(), userid, todoid);


        } else {
            throw new EntityExistsException("Role and User Combination Already Exists");
        }

    }

    @Override
    public List<JustTheCount> getUserTodoCount(){
        List<JustTheCount> usersCounts = new ArrayList<>();

        userrepos.getUserTodoCount().iterator().forEachRemaining(usersCounts::add);

        return usersCounts;

    }
}