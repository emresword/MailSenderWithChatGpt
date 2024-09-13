package com.emailSenderWithChat.emailSenderWithChat.core.mail;

public interface MailService {
	String sendMail(String userMail,String otherUserMail,String message);
    void readEmail(String userMail, String userPassword);
}
