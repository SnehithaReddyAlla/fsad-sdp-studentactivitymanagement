package com.klef.fsad.sdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsad.sdp.dto.ContactDTO;

@RestController
@CrossOrigin("*")
public class ContactController 
{
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String receiverMail;

    @PostMapping("/contact/send")
    public ResponseEntity<String> sendContactMail(@RequestBody ContactDTO dto)
    {
        try
        {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(receiverMail);
            message.setSubject("Student Activities System - Contact Request");

            message.setText(
                "Name: " + dto.getName() + "\n" +
                "Email: " + dto.getEmail() + "\n" +
                "Contact: " + dto.getContact() + "\n\n" +
                "Problem Description:\n" + dto.getDescription()
            );

            mailSender.send(message);

            return ResponseEntity.ok("Message Sent Successfully");
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Failed to Send Message");
        }
    }
}