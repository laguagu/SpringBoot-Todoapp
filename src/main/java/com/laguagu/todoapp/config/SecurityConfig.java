package com.laguagu.todoapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Autowired
//    private CustomUserDetailsService userDetailsService;
////
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userDetailsService)
////                .passwordEncoder(passwordEncoder());
////    }
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user1 = User.withUsername("user1")
//                .password(passwordEncoder().encode("user1Pass"))
//                .roles("USER")
//                .build();
//        UserDetails user2 = User.withUsername("user2")
//                .password(passwordEncoder().encode("user2Pass"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder().encode("adminPass"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2, admin);
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/todos/secured").hasRole("ADMIN");
                    auth.requestMatchers("/login").permitAll();
                    auth.anyRequest().permitAll();
                })
                .httpBasic(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin
                        .successHandler((request, response, authentication) -> {
                            logger.info("Autentikointi onnistui: {}", authentication.getName());
                            logger.info("Principal: {}", authentication.getPrincipal().getClass().getName());
                            response.sendRedirect("/");
                        })
                        .failureHandler((request, response, exception) -> {
                            logger.error("Autentikointi epäonnistui: {}", exception.getMessage());
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true) // Tuhoaa HTTP istunnon
                        .deleteCookies("JSESSIONID") // Käyttäjän cookie tunniste
                )
                .build();
    }


}