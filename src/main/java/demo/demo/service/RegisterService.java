package demo.demo.service;

import demo.demo.entity.Users;
import demo.demo.entity.VerificationToken;
import demo.demo.repository.UserRepository;
import demo.demo.repository.VerificationTokenRepository;
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

        emailService.EmailSendVerification(users,token);
        System.out.println("check out email");
        return "Check our email";
    }
}
