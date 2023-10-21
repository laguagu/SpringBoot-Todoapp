package com.laguagu.todoapp.config;

import com.laguagu.todoapp.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/todos/secured").hasRole("ADMIN");
                    auth.requestMatchers("/login").permitAll();
                    auth.requestMatchers("/logout").permitAll();
                    auth.requestMatchers("/admin").hasRole("ADMIN");
//                    auth.requestMatchers(HttpMethod.POST).permitAll();
                    auth.requestMatchers(HttpMethod.OPTIONS).permitAll(); // Tähän virhe riviin upposi 2 päivää
                    auth.anyRequest().authenticated();
                })

                .userDetailsService(customUserDetailsService)
//                .httpBasic(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin
                        .successHandler((request, response, authentication) -> {
                            logger.info("Autentikointi onnistui: {}", authentication.getName());
                            logger.info("Principal: {}", authentication.getPrincipal().getClass().getName());
                            response.sendRedirect("http://localhost:8080/");
                        })
                        .failureHandler((request, response, exception) -> {
                            logger.error("Autentikointi epäonnistui: {}", exception.getMessage());
                            response.sendRedirect("http://localhost:8080/login");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logoutUser")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true) // Tuhoaa HTTP istunnon
                        .deleteCookies("JSESSIONID") // Käyttäjän cookie tunniste
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}