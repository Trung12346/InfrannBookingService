package demo.demo.Service;

import demo.demo.Entity.Users;
import demo.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    public Users FindByName(String name){
        return userRepository.findByUsername(name);
    }

    public boolean login(String username, String password) throws Exception {
        Users users = userRepository.findByUsername(username);
        if(users == null){
            throw new UsernameNotFoundException("Username not found exception");
        }
        if(!users.isEnabled()){
            throw new Exception("User is not enabled");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        if(authentication.isAuthenticated()!=false){
            return true;
        }
        return false;
    }
}
