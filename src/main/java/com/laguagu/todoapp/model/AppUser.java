package com.laguagu.todoapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Username cannot be null")
    @Size(min = 3, max = 15, message = "Username must be between 3 - 15 characters")
    private String username;
    @Size(min = 3, message = "Password too short")
    @NotNull(message = "Password cannot be null")
    private String password;
    private String roles;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Todo> todos = new ArrayList<>();
    public AppUser() {
    }

    public AppUser(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                ", todos=" + todos +
                '}';
    }
}
