package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.VerificationToken;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class PasswordResetService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public boolean requestPasswordReset(String email) {
        if(!userRepository.existsByEmail(email)) {
            return false;
        }
        try {
            String token = UUID.randomUUID().toString();
            User user = userRepository.findByEmail(email);
            verificationTokenRepository.save(new VerificationToken(token, user));
            emailService.EmailSendVerification(user, token, EmailService.PASSWORD_RESET);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }


}
