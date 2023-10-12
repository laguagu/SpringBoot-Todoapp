package com.laguagu.todoapp.controller;

import com.laguagu.todoapp.model.Todo;
import com.laguagu.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/todos")
@CrossOrigin
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/")
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @GetMapping("/secured")
    public String secured(Principal principal) {
        return "Hello, Secured "+ principal.getName();
    }

    @PostMapping("/")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
        Todo savedTodo = todoRepository.save(todo);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        if (todoRepository.existsById(id)) {
            todo.setId(id);
            Todo updatedTodo = todoRepository.save(todo);
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
