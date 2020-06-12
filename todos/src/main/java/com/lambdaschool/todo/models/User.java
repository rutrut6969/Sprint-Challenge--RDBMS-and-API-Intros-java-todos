package com.lambdaschool.todo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long userid;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String primaryemail;

    @Column(nullable = false)
    private String password;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
            @JsonIgnoreProperties(value = "user")
    List<Todo> todos = new ArrayList<>();

    public User() {
    }

    public User(String username, String primaryemail, String password) {
        this.username = username;
        this.primaryemail = primaryemail;
        this.password = password;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrimaryemail() {
        return primaryemail;
    }

    public void setPrimaryemail(String primaryemail) {
        this.primaryemail = primaryemail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }
}
