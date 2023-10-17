package com.laguagu.todoapp;

import com.laguagu.todoapp.controller.TodoController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TodoAppApplicationTests {

	@Autowired
	private TodoController todoController;

	@Test
	void contextLoads() throws Exception{
		Assertions.assertThat(todoController).isNotNull();
	}
}
