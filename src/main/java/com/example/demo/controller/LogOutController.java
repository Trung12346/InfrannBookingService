package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/logout")
@Controller
public class LogOutController {
    @GetMapping("")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}
