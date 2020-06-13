package com.lambdaschool.todo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "usertodos", uniqueConstraints = {@UniqueConstraint(columnNames = {"userid", "todoid"})})
public class UserTodos extends Auditable implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "todos")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "todoid")
    @JsonIgnoreProperties(value = "users")
    private Todo todo;

    public UserTodos() {
    }

    public UserTodos(User user, Todo todo) {
        this.user = user;
        this.todo = todo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTodos userTodos = (UserTodos) o;
        return Objects.equals(user, userTodos.user) &&
                Objects.equals(todo, userTodos.todo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, todo);
    }
}
