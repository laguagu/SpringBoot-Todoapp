package com.laguagu.todoapp.controller;

import com.laguagu.todoapp.model.AppUser;
import com.laguagu.todoapp.model.SecurityAppUser;
import com.laguagu.todoapp.model.Todo;
import com.laguagu.todoapp.repository.AppUserRepository;
import com.laguagu.todoapp.repository.TodoRepository;
import com.laguagu.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/todos")
@CrossOrigin(origins = "http://localhost:5173")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public ResponseEntity<List<Todo>> getAllTodosForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityAppUser userDetails = (SecurityAppUser) auth.getPrincipal();

        Long userId = userDetails.getAppUser().getId();

        List<Todo> tasksForUser = todoRepository.findByUserId(userId);
        return ResponseEntity.ok(tasksForUser);
    }

    @GetMapping("/secured")
    public String secured(Principal principal) {
        return "Hello, Secured "+ principal.getName();
    }

    @PostMapping("/")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo, Principal principal) {
        try {
            Todo savedTodo = todoService.saveTodoForCurrentUser(todo, principal.getName());
            return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        Optional<Todo> exitingTodo = todoRepository.findById(id);

        if (!exitingTodo.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityAppUser userDetails = (SecurityAppUser) auth.getPrincipal();

        Long userId = userDetails.getAppUser().getId();

        //Tarkistus onko nykyinen käyttäjä sama kuin tehtävän omistaja
        if(!exitingTodo.get().getUser().getId().equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        todo.setId(id);
        todo.setUser(exitingTodo.get().getUser());
        Todo updatedTodo = todoRepository.save(todo);
        return new ResponseEntity<>(updatedTodo,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            todoRepository.deleteById(id);
            return new ResponseEntity<>(todo.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
