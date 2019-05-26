package pl.masi.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private MailSender mailSender;

    public void sendEmail(String to, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("masiblack2019@gmail.com");
        mailMessage.setTo(to);
        mailMessage.setSubject("Wynik testu rekrutacyjnego");
        mailMessage.setText(content);

        mailSender.send(mailMessage);
    }
}

