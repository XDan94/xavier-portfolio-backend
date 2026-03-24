package fr.xavier.portfolio.api.service;

import fr.xavier.portfolio.api.domain.ContactRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${contact.mail.to}")
    private String mailTo;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContactEmail(ContactRequest request) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(mailTo);
        helper.setReplyTo(request.email());
        helper.setSubject("[Portfolio] " + request.subject());
        helper.setText(buildEmailBody(request), true);

        mailSender.send(message);
    }

    private String buildEmailBody(ContactRequest request) {
        return """
            <html><body style="font-family: Arial, sans-serif; color: #333;">
                <h2 style="color: #6e46dc;">Nouveau message de contact</h2>
                <table style="border-collapse: collapse; width: 100%;">
                    <tr>
                        <td style="padding: 8px; font-weight: bold;">Nom :</td>
                        <td style="padding: 8px;">%s</td>
                    </tr>
                    <tr style="background: #f5f5f5;">
                        <td style="padding: 8px; font-weight: bold;">Email :</td>
                        <td style="padding: 8px;"><a href="mailto:%s">%s</a></td>
                    </tr>
                    <tr>
                        <td style="padding: 8px; font-weight: bold;">Sujet :</td>
                        <td style="padding: 8px;">%s</td>
                    </tr>
                    <tr style="background: #f5f5f5;">
                        <td style="padding: 8px; font-weight: bold;">Message :</td>
                        <td style="padding: 8px;">%s</td>
                    </tr>
                </table>
            </body></html>
            """.formatted(
                request.name(),
                request.email(), request.email(),
                request.subject(),
                request.message().replace("\n", "<br/>")
        );
    }
}