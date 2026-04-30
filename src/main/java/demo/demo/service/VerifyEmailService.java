package demo.demo.service;

import demo.demo.entity.Users;
import demo.demo.entity.VerificationToken;
import demo.demo.repository.UserRepository;
import demo.demo.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyEmailService {

    @Autowired
    VerificationTokenRepository verificationToken;

    @Autowired
    UserRepository userRepository;

    public String veirfyToken(String token){
        if(verificationToken.findByToken(token).isUsed()){
            return "This Token is Already Used";
        }
        if(verificationToken.findByToken(token).isExpired()){
            return "this token is Already Expire";
        }
        VerificationToken vt = verificationToken.findByToken(token);
        Users users = vt.getUsers();
        users.setEnabled(true);
        users.setRole(0);
        userRepository.save(users);
        vt.setUsed(true);
        verificationToken.save(vt);
        return "redirect:/Login";
    }
}
