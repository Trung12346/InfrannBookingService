package com.example.demo.controller;

import com.example.demo.model.VerificationToken;
import com.example.demo.repository.VerificationTokenRepository;
import com.example.demo.service.PasswordResetService;
import com.example.demo.service.UserService;
import com.example.demo.service.VerifyEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/password-reset")
public class PasswordResetController {
    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    VerifyEmailService verifyEmailService;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    UserService userService;

    @GetMapping("/email")
    public String get_0() {
        return "password-reset-email";
    }

    @PostMapping("/email")
    public String post_0(@RequestParam("email") String email,
                         Model model) {
        model.addAttribute("emailSent", null);
        if (passwordResetService.requestPasswordReset(email)) {
            model.addAttribute("emailSent", true);
        } else {
            model.addAttribute("emailSent", false);
        }

        return "password-reset-email";
    }

    @GetMapping("/password")
    public String get_1(@RequestParam("token") String token,
                        Model model) {
        if (verifyEmailService.tokenIsValid(token)) {
            model.addAttribute("token", token);
            return "password-reset-password";
        } else {
            return "login";
        }
    }

    @PostMapping("")
    public String post_1(@RequestParam("token") String token,
                         @RequestParam("password") String password,
                         @RequestParam("passwordConfirm") String passwordConfirm) {
        System.out.println("see if token is valid");
        if (verifyEmailService.tokenIsValid(token)) {
            VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
            System.out.println("changing password");
            userService.changePassword(verificationToken.getUsers(), password, passwordConfirm);
            System.out.println("putting token to unusable");
            verifyEmailService.deprecateToken(verificationToken);

            return "redirect:/login";
        }
        return "redirect:/password-reset/password";
    }
}
