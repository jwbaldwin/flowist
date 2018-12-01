package com.jbaldwin.novu.service;

import com.jbaldwin.novu.domain.User;
import com.jbaldwin.novu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class UserService {

    @Autowired
    UserRepository userRepository;

    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }
}
