package com.example.demo.controller;

import com.example.demo.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    UsersDetailsService usersDetailsService;



    @GetMapping("/admin")
    public String Admin(Model model, Authentication authentication){
        if(authentication.getPrincipal() instanceof OidcUser){
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            String email = oidcUser.getAttribute("email");
            Users user = userRepository.findByEmail(email);
            model.addAttribute("User",user.getUsername());
            model.addAttribute("id",user.getId());
        }else{
            model.addAttribute("User",authentication.getName());
            model.addAttribute("id",userRepository.findByUsername(authentication.getName()).getId());
        }
        return "admin";
    }
}
