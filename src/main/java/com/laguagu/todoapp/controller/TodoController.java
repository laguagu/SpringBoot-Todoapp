package com.laguagu.todoapp.controller;

import com.laguagu.todoapp.model.Todo;
import com.laguagu.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

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
    public String secured() {
        return "Hello, Secured";
    }

    @PostMapping("/")
    public Todo addTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        if (todoRepository.existsById(id)) {
            todo.setId(id);
            return todoRepository.save(todo);
        }
        return null;
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
