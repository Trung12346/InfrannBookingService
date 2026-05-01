package com.example.demo.service;

import com.example.demo.model.VerificationToken;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationTokenRepository;
import com.example.demo.model.Users;
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

    public String register(Users users,String cf_password){
        if(userRepository.existsByEmail(users.getEmail())){
            return "Email Already exists";
        }
        if(!users.getPassword_hash().trim().equals(cf_password.trim())) {
            return "The password and Confirmpassword not matchse";
        }
        users.setPassword_hash(passwordEncoder.encode(cf_password));
        users.setEnabled(false);
        userRepository.save(users);

        String token = UUID.randomUUID().toString();

        verificationTokenRepository.save(new VerificationToken(token,users));

        emailService.EmailSendVerification(users,token, EmailService.ACCOUNT_VERIFY);
        System.out.println("check out email");
        return "Check our email";
    }

}
