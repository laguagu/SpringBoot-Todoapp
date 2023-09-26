package com.laguagu.todoapp.repository;

import com.laguagu.todoapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository <Todo, Long> {

}
