package com.jbaldwin.novu.web.controller;

import com.jbaldwin.novu.domain.User;
import com.jbaldwin.novu.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User saveUser() {
        return userService.saveUser("james", "baldwin");
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
    }

}
