package com.example.demo.service;

import com.example.demo.model.User;
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

    public boolean changePassword(User user, String password, String passwordConfirm) {
        if(password.trim().equals(passwordConfirm.trim())) {
            user.setPassword_hash(passwordEncoder.encode(password));
            userRepository.save(user);
        }

        return true;
    }
}
