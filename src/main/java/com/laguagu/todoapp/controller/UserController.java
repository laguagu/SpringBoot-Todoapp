package com.laguagu.todoapp.controller;
import com.laguagu.todoapp.model.AppUser;
import com.laguagu.todoapp.model.SecurityAppUser;
import com.laguagu.todoapp.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping("api/user/me")
    public ResponseEntity<?> currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            SecurityAppUser userDetails = (SecurityAppUser) auth.getPrincipal();
            logger.info("Käyttäjän tiedot: {}", userDetails);
            return ResponseEntity.ok((UserDetails) auth.getPrincipal());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Käyttäjä ei ole kirjautunut sisälle"));
        }
    }

    @PostMapping("api/user/")
    public ResponseEntity<AppUser> addUser(@RequestBody AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        AppUser newUser = appUserRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("api/user/")
    public ResponseEntity<List<AppUser>> getAllUsers(AppUser appUser) {
        List<AppUser> allUsers = appUserRepository.findAll();
        return ResponseEntity.ok(allUsers);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin(){
        return "Hello admin";
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    public String user(){
        return "Hello user";
    }

}
