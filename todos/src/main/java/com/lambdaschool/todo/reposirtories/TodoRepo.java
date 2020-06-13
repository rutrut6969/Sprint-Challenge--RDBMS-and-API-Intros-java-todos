package com.lambdaschool.todo.reposirtories;

import com.lambdaschool.todo.models.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepo extends CrudRepository<Todo, Long> {

}
