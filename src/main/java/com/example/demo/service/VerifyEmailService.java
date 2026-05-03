package com.example.demo.service;

import com.example.demo.enumRole.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationTokenRepository;
import com.example.demo.model.User;
import com.example.demo.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyEmailService {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    UserRepository userRepository;

    public String veirifyToken(String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken.isUsed()){
            return "This Token is Already Used";
        }
        if(verificationToken.isExpired()){
            return "this token is Already Expire";
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);
        user.setRole(Role.GUEST.getValue());
        userRepository.save(user);
        verificationToken.setUsed(true);
        verificationTokenRepository.save(verificationToken);
        return "redirect:/Login";
    }
    public boolean tokenIsValid(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken.isUsed() || verificationToken.isExpired()) {
            return false;
        }
        System.out.println("token valid");
        return true;
    }
    public void deprecateToken(VerificationToken verificationToken) {
        verificationToken.setUsed(true);
        verificationTokenRepository.save(verificationToken);
    }
}
