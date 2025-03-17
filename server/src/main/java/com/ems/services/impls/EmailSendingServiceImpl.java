package com.ems.services.impls;


import com.ems.events.EmailSendEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@KafkaListener(topics = "email-send-event-topic" , groupId="update-voter-events-topic")
@Data
@Slf4j
@Service
@KafkaListener(topics = "email-send-event-topic" , groupId="update-voter-events-topic")
public class EmailSendingServiceImpl {

    private final JavaMailSender mailSender;
    @Async
    @KafkaHandler
    public void sendMail(EmailSendEvent emailSendEvent){
        System.out.println("in my email service************************************");
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
            System.out.println("in email service---------------------------------------------------------------");
            helper.setTo(emailSendEvent.getReceiverMailAddress());
            helper.setSubject(emailSendEvent.getEmailSubject());
            helper.setText(emailSendEvent.getMailBody(), true);

            ClassPathResource image = new ClassPathResource("static/image.png");// true enables HTML content

            helper.addInline("companyLogo", image);
            mailSender.send(message);
            log.info("Email sent successfully to {}", emailSendEvent.getReceiverMailAddress());
        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", emailSendEvent.getReceiverMailAddress(), e.getMessage());
        }
    }
}
