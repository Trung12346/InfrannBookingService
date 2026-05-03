package com.example.demo.service;

import com.example.demo.model.VerificationToken;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationTokenRepository;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;;

    public String register(User user, String cf_password){
        if(userRepository.existsByEmail(user.getEmail())){
            return "Email Already exists";
        }
        if(!user.getPassword_hash().trim().equals(cf_password.trim())) {
            return "The password and Confirmpassword not matchse";
        }
        user.setPassword_hash(passwordEncoder.encode(cf_password));
        user.setEnabled(false);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        verificationTokenRepository.save(new VerificationToken(token, user));

        emailService.EmailSendVerification(user,token, EmailService.ACCOUNT_VERIFY);
        System.out.println("check out email");
        return "Check our email";
    }

}
