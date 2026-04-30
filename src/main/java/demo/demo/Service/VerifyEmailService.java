package demo.demo.Service;

import demo.demo.Entity.Users;
import demo.demo.Entity.Verification_token;
import demo.demo.Repository.UserRepository;
import demo.demo.Repository.Verification_TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VerifyEmailService {

    @Autowired
    Verification_TokenRepository verificationToken;

    @Autowired
    UserRepository userRepository;

    public String veirfyToken(String token){
        if(verificationToken.findByToken(token).isUsed()){
            return "This Token is Already Used";
        }
        if(verificationToken.findByToken(token).isExpired()){
            return "this token is Already Expire";
        }
        Verification_token vt = verificationToken.findByToken(token);
        Users users = vt.getUsers();
        users.setEnabled(true);
        users.setRole(0);
        userRepository.save(users);
        vt.setUsed(true);
        verificationToken.save(vt);
        return "redirect:/Login";
    }
}
