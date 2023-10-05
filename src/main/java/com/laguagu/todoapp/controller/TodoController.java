package com.laguagu.todoapp.controller;

import com.laguagu.todoapp.model.Todo;
import com.laguagu.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @CrossOrigin(origins = "http://localhost:8080")
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
        if(todoRepository.existsById(id)) {
            todo.setId(id);
            return todoRepository.save(todo);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }

}
