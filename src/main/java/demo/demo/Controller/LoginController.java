package demo.demo.Controller;

import demo.demo.Entity.Users;
import demo.demo.Repository.UserRepository;
import demo.demo.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/Login")
@Controller
public class LoginController {

    @Autowired
    LoginService loginService;


    @GetMapping("")
    public String Login(){
        return "Login";
    }
}
