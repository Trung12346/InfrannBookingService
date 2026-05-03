package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.EmailService;
import com.example.demo.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/register")
@Controller
public class RegisterController {

    @Autowired
    EmailService emailService;

    @Autowired
    RegisterService registerService;

    @GetMapping("")
    public String Register(Model model){
        model.addAttribute("item",new User());
        return "register";
    }

    @PostMapping("")
    public String register(@ModelAttribute("item") User item,
                           @RequestParam("cf_password") String cf_password){
        registerService.register(item,cf_password);
        return "redirect:/login";
    }
}
