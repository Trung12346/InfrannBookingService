package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean changePassword(Users users, String password, String passwordConfirm) {
        if(password.trim().equals(passwordConfirm.trim())) {
            users.setPassword_hash(passwordEncoder.encode(password));
            userRepository.save(users);
        }

        return true;
    }
}
