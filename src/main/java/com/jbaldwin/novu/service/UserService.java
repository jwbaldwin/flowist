package com.jbaldwin.novu.service;

import com.jbaldwin.novu.domain.User;
import com.jbaldwin.novu.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(String firstName, String lastName) {
        return userRepository.save(new User(firstName, lastName));
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(UUID.fromString(id));
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(UUID.fromString(id));
    }
}
