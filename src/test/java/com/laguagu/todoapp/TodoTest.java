package com.laguagu.todoapp;


import com.laguagu.todoapp.model.AppUser;
import com.laguagu.todoapp.model.Todo;
import com.laguagu.todoapp.repository.AppUserRepository;
import com.laguagu.todoapp.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodoTest {
    private static final Logger logger = LoggerFactory.getLogger(TodoTest.class);
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    AppUserRepository userRepository;
    @Autowired
    private MockMvc mockMvc; // Komponentti jonka avulla voi tehdä HTTP pyyntö
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Value(value = "${local.server.port}")
    private int port;

    @Test
    public void addNewUserWithEmptyTodos() {
        AppUser user = new AppUser("user", "pass", "ROLE_USER");
        userRepository.save(user);
        List<Todo> todos = todoRepository.findByUserId(user.getId());
        logger.info("Käyttäjä lisätty: {}, Running at port {}", user, port);
        logger.info("Todo count: {}, First todo Description: {}, Onko todo tyhjä : {}", todos.size(), todos.stream().findFirst(), todos.isEmpty());
        assertEquals(todos.size(), 0);
    }

    @Test
    public void greetingShouldReturnSignInSuggestion() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Please sign in");
    }

    @Test
    public void testCurrentUserEndpoint() throws Exception {
        // Luodaan uusi käyttäjä
        AppUser newUser = new AppUser("testUser", passwordEncoder.encode("testPassword"), "ROLE_USER");
        userRepository.save(newUser);
        logger.info("uusiKäyttäjä {}",newUser);

        // Simuloi kirjautumista (token tai sessio) - tämä riippuu siitä, kuinka olet määrittänyt Spring Securityn.

        // Tehdään pyyntö /api/user/me endpointtiin
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me")
                        .with(user("testUser").password("testPassword").roles("USER")))  // Simuloi kirjautumista
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorities[0].authority").value("ROLE_USER"));
                                                                // $ merkki on osa JSONPath kyselyä  ja $ edustaa "juuritasoa"
    }
}