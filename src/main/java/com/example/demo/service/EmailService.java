package com.example.demo.service;

import com.example.demo.model.Users;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final MailSender mailSender;
    @Value("${app.base-url}")
    private String baseUrl;

    public static final int PASSWORD_RESET = 0, ACCOUNT_VERIFY = 1;

    public void EmailSendVerification(Users users, String token, int mode){
        String VerifyLink = "";
        if (mode == 0) {
            VerifyLink = baseUrl + "/password-reset/password?token=" + token;
        } else if (mode == 1) {
            VerifyLink = baseUrl + "/verify-email?token=" + token;
        }
        SimpleMailMessage ms = new SimpleMailMessage();
        ms.setTo("This is Your Email Verification");
        ms.setTo(users.getEmail());
        ms.setSubject("Authentication Account");
        ms.setText(users.getEmail()+ "Click this link to verify your mail: <a href=\"" + VerifyLink + "\"></a><br><button onclick=\"window.location.href='" + VerifyLink + "'\"></button>");
        mailSender.send(ms);

    }
}
