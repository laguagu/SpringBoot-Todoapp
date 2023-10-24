package com.laguagu.todoapp;

import com.laguagu.todoapp.model.AppUser;
import com.laguagu.todoapp.model.Todo;
import com.laguagu.todoapp.repository.AppUserRepository;
import com.laguagu.todoapp.repository.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(TodoRepository todo, AppUserRepository users, PasswordEncoder encoder) {
		return args -> {
			AppUser user1 = new AppUser("user",encoder.encode("pass"),"ROLE_USER");
			users.save(user1);
			users.save(new AppUser("admin",encoder.encode("pass"),"ROLE_USER,ROLE_ADMIN"));
			todo.save(new Todo(user1,"otsikko",false));
			System.out.println(user1);
		};
	}

}
