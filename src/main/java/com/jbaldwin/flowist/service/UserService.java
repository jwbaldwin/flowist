package com.jbaldwin.flowist.service;

import com.jbaldwin.flowist.domain.User;
import com.jbaldwin.flowist.repository.UserRepository;
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

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }
}
