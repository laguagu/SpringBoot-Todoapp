package com.laguagu.todoapp.service;

import com.laguagu.todoapp.model.AppUser;
import com.laguagu.todoapp.model.Todo;
import com.laguagu.todoapp.repository.AppUserRepository;
import com.laguagu.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    public Todo saveTodoForCurrentUser(Todo todo, String username) {
        AppUser currentUser = appUserRepository.findByUsername(username).orElse(null);

        if (currentUser == null) {
            throw new RuntimeException("Käyttäjää ei löytynyt.");
        }

        todo.setUser(currentUser);
        return todoRepository.save(todo);
    }
}
