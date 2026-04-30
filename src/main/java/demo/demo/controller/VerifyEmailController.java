package demo.demo.controller;

import demo.demo.service.VerifyEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VerifyEmailController {

    @Autowired
    VerifyEmailService service;

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam String token){
        return service.veirfyToken(token);
    }

}
