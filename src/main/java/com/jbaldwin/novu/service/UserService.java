package com.jbaldwin.novu.service;

import com.jbaldwin.novu.dao.UserRepository;
import com.jbaldwin.novu.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(String firstName, String lastName) {
        return userRepository.save(new User(firstName, lastName));
    }
}
