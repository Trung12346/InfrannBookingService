package demo.demo.service;

import demo.demo.entity.Users;
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

    public void EmailSendVerification(Users users, String token){
        String VerifyLink = baseUrl + "/verify-email?token="+token;

        SimpleMailMessage ms = new SimpleMailMessage();
        ms.setTo("This is Your Email Verification");
        ms.setTo(users.getEmail());
        ms.setSubject("Authentication Account");
        ms.setText(users.getEmail()+ "Click this link to verify your mail: " + VerifyLink);
        mailSender.send(ms);

    }
}
