package com.sudhanshu.employeeleavemanagementsystem.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Existing Plain Text Email
    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    // New HTML Email
    public void sendHtmlEmail(String to, String subject, String htmlBody) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
    
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");
    
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
    
            mailSender.send(message);
    
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send HTML email", e);
        }
    }
}