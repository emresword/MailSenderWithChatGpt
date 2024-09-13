package com.emailSenderWithChat.emailSenderWithChat.business.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptRequest {
    private String model;
    private String prompt;  
    private int max_tokens; 
    private double temperature; 
}
