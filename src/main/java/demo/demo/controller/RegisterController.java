package demo.demo.controller;

import demo.demo.entity.Users;
import demo.demo.service.EmailService;
import demo.demo.service.RegisterService;
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
        model.addAttribute("item",new Users());
        return "Register";
    }

    @PostMapping("")
    public String register(@ModelAttribute("item")Users item,
                           @RequestParam("cf_password") String cf_password){
        registerService.register(item,cf_password);
        return "redirect:/Login";
    }
}
