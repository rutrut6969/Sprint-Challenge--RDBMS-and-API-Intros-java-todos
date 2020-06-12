package com.lambdaschool.todo.reposirtories;

import com.lambdaschool.todo.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface UserRepo extends CrudRepository<User, Long> {
    // Finding user by username
    User findByUsername(String uname);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM usertodos WHERE userid = :userid AND todois = :todoid", nativeQuery = true)
    void deleteUserTodos(long userid, long todoid);
}
