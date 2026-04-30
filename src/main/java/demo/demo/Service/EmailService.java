package demo.demo.Service;

import demo.demo.Entity.Users;
import demo.demo.Entity.Verification_token;
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
        ms.setTo("This is You Email Verification");
        ms.setTo(users.getEmail());
        ms.setSubject("Authentication Account");
        ms.setText(users.getEmail()+ "Click this link to verify your mail: " + VerifyLink);
        mailSender.send(ms);

    }
}
