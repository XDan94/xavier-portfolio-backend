package fr.xavier.portfolio.api.service;

import fr.xavier.portfolio.api.domain.ContactRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${mail.to}")
    private String toEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContactEmail(ContactRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setReplyTo(request.email());
        message.setSubject("Nouveau message de contact : " + request.subject());
        message.setText(
                "Nom : " + request.name() + "\n" +
                        "Email : " + request.email() + "\n" +
                        "Sujet : " + request.subject() + "\n\n" +
                        "Message :\n" + request.message()
        );

        mailSender.send(message);
    }
}