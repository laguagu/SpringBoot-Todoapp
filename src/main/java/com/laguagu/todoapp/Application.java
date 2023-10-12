package com.laguagu.todoapp;

import com.laguagu.todoapp.model.AppUser;
import com.laguagu.todoapp.model.Todo;
import com.laguagu.todoapp.repository.AppUserRepository;
import com.laguagu.todoapp.repository.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(TodoRepository todo, AppUserRepository users) {
		return args -> {
			users.save(new AppUser("user","password","ROLE_USER"));
			users.save(new AppUser("admin","password","ROLE_USER,ROLE_ADMIN"));
			todo.save(new Todo("description",false));
		};
	}

}
