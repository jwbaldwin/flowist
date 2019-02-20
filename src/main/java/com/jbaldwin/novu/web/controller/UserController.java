package com.jbaldwin.novu.web.controller;

import com.jbaldwin.novu.domain.User;
import com.jbaldwin.novu.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public Optional<User> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @PostMapping("/user")
    public User saveUser() {
        return userService.saveUser("james", "baldwin");
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUserById(id);
    }

}
