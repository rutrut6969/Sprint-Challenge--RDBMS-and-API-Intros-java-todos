package com.lambdaschool.todo.reposirtories;

import com.lambdaschool.todo.models.User;
import com.lambdaschool.todo.views.JustTheCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {
    // Finding user by username
    User findByUsername(String uname);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM usertodos WHERE userid = :userid AND todois = :todoid", nativeQuery = true)
    void deleteUserTodos(long userid, long todoid);

    @Query(value = "SELECT COUNT(*) as count FROM usertodos WHERE userid = :userid AND todoid = :todoid", nativeQuery = true)
    JustTheCount checkUserTodosCombo(long userid, long todoid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO userroles(userid, todoid, created_by, created_date, last_modified_by, last_modified_date) VALUES (:userid, :todoid , :uname, CURRENT_TIMESTAMP, :uname, CURRENT_TIMESTAMP)",
            nativeQuery = true)    void insertUserTodos(String uname, long userid, long todoid);

    @Query(value = "SELECT userid, COUNT(*) as count FROM users RIGHT JOIN todos GROUP BY userid ORDER BY userid", nativeQuery = true)
    List<JustTheCount> getUserTodoCount();

}
