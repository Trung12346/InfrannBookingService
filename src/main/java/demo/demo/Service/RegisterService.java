package demo.demo.Service;

import demo.demo.Entity.Users;
import demo.demo.Entity.Verification_token;
import demo.demo.Repository.UserRepository;
import demo.demo.Repository.Verification_TokenRepository;
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
    Verification_TokenRepository verificationTokenRepository;;

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

        verificationTokenRepository.save(new Verification_token(token,users));

        emailService.EmailSendVerification(users,token);
        System.out.println("check out email");
        return "Check our email";
    }
}
