package com.emailSenderWithChat.emailSenderWithChat.api.controllers;

import com.emailSenderWithChat.emailSenderWithChat.business.abstracts.MessageService;
import com.emailSenderWithChat.emailSenderWithChat.business.response.ChatGptResponse;
import com.emailSenderWithChat.emailSenderWithChat.business.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/analyze")
    public ChatGptResponse analyzeMessage(@RequestBody String message) {
        return messageService.analyzeMessage(message);
    }
}
