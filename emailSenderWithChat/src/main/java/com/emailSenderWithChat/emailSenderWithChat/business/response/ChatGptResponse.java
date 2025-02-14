package com.emailSenderWithChat.emailSenderWithChat.business.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptResponse {
    private String id;
    private String object;
    private String created;
    private String model;
    private String content; 
}
