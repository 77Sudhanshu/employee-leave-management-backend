package com.sudhanshu.employeeleavemanagementsystem.controller;

import com.sudhanshu.employeeleavemanagementsystem.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/test")
    public String sendTestEmail() {

        emailService.sendEmail(
                "sudhanshubhardwaj2332@gmail.com",
                "Spring Boot Test",
                "Congratulations! Your email service is working."
        );

        return "Email Sent Successfully!";
    }
}