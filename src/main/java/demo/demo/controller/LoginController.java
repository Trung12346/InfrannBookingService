package demo.demo.controller;

import demo.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
