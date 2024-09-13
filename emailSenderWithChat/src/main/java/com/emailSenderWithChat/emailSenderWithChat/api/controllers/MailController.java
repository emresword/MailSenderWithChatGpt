package com.emailSenderWithChat.emailSenderWithChat.api.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.emailSenderWithChat.emailSenderWithChat.core.mail.MailService;

@RestController
@RequestMapping("/api/mails")
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public String sendMail(@RequestParam String userMail,
                           @RequestParam String otherUserMail,
                           @RequestParam String mailMessage) {
        return mailService.sendMail(userMail, otherUserMail, mailMessage);
    }

    @PostMapping("/read")
    public void readEmail(@RequestParam String userMail, @RequestParam String userPassword) {
        mailService.readEmail(userMail, userPassword);
    }
}
