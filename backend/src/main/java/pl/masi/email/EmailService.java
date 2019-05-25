package pl.masi.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

@Autowired
    private EmailConfiguration emailCfg;

    public void sendEmail(String to, String content) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailCfg.getHost());
        mailSender.setPort(this.emailCfg.getPort());
        mailSender.setUsername(this.emailCfg.getUsername());
        mailSender.setPassword(this.emailCfg.getPassword());


        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("masiblack2019@gmail.com");
        mailMessage.setTo(to);
        mailMessage.setSubject("Wynik testu rekrutacyjnego");
        mailMessage.setText(content);

        mailSender.send(mailMessage);
        }

    }

