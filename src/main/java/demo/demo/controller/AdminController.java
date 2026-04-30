package demo.demo.controller;

import demo.demo.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    UsersDetailsService usersDetailsService;




    @GetMapping("/Admin")
    public String Admin(Model model, Authentication authentication){

        model.addAttribute("User",authentication.getName());
        return "Admin";
    }
}
