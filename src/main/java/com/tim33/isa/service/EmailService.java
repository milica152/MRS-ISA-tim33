package com.tim33.isa.service;

import com.tim33.isa.model.User;
import com.tim33.isa.model.UserVerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserVerificationTokenService tokenService;

    @Async
    public void sendVerificationEmail(User user) {
        String uuid = UUID.randomUUID().toString();
        UserVerificationToken token = new UserVerificationToken();
        token.setToken(uuid);
        token.setUser(user);
        tokenService.save(token);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("isa@vpetrovic.com");
        mail.setSubject("Complete registration on PomozBoze");
        String content = null;
        try {
            content = String.format(
                    "Hello %s,\nYou must activate your account. Please do so by following the link below.\n\nhttp://localhost:8080/acc_confirm?token=%s",
                    user.getUsername(), URLEncoder.encode(uuid, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mail.setText(content);
        mailSender.send(mail);
    }

}
