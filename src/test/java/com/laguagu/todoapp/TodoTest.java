package com.laguagu.todoapp;


import com.laguagu.todoapp.model.AppUser;
import com.laguagu.todoapp.model.Todo;
import com.laguagu.todoapp.repository.AppUserRepository;
import com.laguagu.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // Spring security otettu pois käytöstä
public class TodoTest {
    private static final Logger logger = LoggerFactory.getLogger(TodoTest.class);
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    AppUserRepository userRepository;
    @Autowired
    private MockMvc mockMvc; // Komponentti jonka avulla voi tehdä HTTP pyyntö
    @Test
    public void addNewUserWithEmptyTodos() {
        AppUser user = new AppUser("user","pass","ROLE_USER");
        userRepository.save(user);
        List<Todo> todos = todoRepository.findByUserId(user.getId());
        logger.info("Käyttäjä lisätty: {}",user);
//        assertEquals(books.get(0).getTitle(), "Kalakirja");
        logger.info("Todo count: {}, First todo Description: {}, Onko todo tyhjä : {}", todos.size(), todos.stream().findFirst(), todos.isEmpty());
        assertEquals(todos.size(),0);
    }
//    @Test
//    public void shouldReturnThreeBooks() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/rest/books"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
//    }
}