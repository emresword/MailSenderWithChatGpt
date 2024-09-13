package com.emailSenderWithChat.emailSenderWithChat.business.abstracts;

import com.emailSenderWithChat.emailSenderWithChat.business.response.ChatGptResponse;
import com.emailSenderWithChat.emailSenderWithChat.business.response.MessageResponse;

public interface MessageService {
	
	ChatGptResponse analyzeMessage(String message);

}
