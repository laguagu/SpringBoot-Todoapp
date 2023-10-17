package com.laguagu.todoapp.controller;

import com.laguagu.todoapp.model.SecurityAppUser;
import com.laguagu.todoapp.model.Todo;

import com.laguagu.todoapp.repository.TodoRepository;
import com.laguagu.todoapp.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("api/todos")
@CrossOrigin
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
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

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        // Hae todo-olio tietyllä id:llä
        Optional<Todo> optionalTodo = todoRepository.findById(id);

        // Jos todoa ei löydy IO:llä
        if (!optionalTodo.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Varmistetaan että todo kuuluu kirjautuneelle käyttäjälle
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityAppUser userDetails = (SecurityAppUser) auth.getPrincipal();
        Long userId = userDetails.getAppUser().getId();

        Todo todo = optionalTodo.get();
        if (!todo.getUser().getId().equals(userId)) {
            // Jos todo ei kuulu tälle käyttäjälle
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Palauta 200 ok
        return ResponseEntity.ok(todo);
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
            String message = "Ei päästä sisään";
            logger.error(message);
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
