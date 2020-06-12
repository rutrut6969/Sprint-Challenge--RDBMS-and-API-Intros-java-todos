package com.lambdaschool.todo.reposirtories;

import com.lambdaschool.todo.models.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepo extends CrudRepository<Todo, Long> {

    // Finding all Todos by Userid
    List<Todo> usertodos(long uid);

}
