package com.emailSenderWithChat.emailSenderWithChat.business.mail;

import jakarta.mail.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.emailSenderWithChat.emailSenderWithChat.business.abstracts.MessageService;
import com.emailSenderWithChat.emailSenderWithChat.business.response.ChatGptResponse;
import com.emailSenderWithChat.emailSenderWithChat.core.mail.MailService;

import java.util.Properties;

@Service
public class MailManager implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageService messageService;

    @Override
    public String sendMail(String userMail, String otherUserMail, String mailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(userMail);
        message.setTo(otherUserMail);
        message.setText(mailMessage);
        message.setSubject("Automated Reply");
        mailSender.send(message);
        return "Mail Sent Successfully!";
    }

    @Override
    public void readEmail(String userMail, String userPassword) {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", "imap.gmail.com");
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.ssl.enable", "true");

        try (Store store = Session.getDefaultInstance(properties).getStore("imaps")) {
            store.connect(userMail, userPassword);  
            try (Folder inbox = store.getFolder("INBOX")) {
                inbox.open(Folder.READ_ONLY);

                Message[] messages = inbox.getMessages();
                if (messages.length > 0) {
                    Message email = messages[messages.length - 1];
                    String subject = email.getSubject();
                    String from = email.getFrom()[0].toString();
                    System.out.println("Subject: " + subject);
                    System.out.println("From: " + from);

                    
                    String emailContent = getEmailContent(email);

                    
                    ChatGptResponse aiResponse = messageService.analyzeMessage(emailContent);
                    System.out.println("AI Response: " + aiResponse.getContent());

                  
                    sendMail(userMail, from, aiResponse.getContent());
                } else {
                    System.out.println("No messages found.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private String getEmailContent(Message email) throws Exception {
        String content = "";
        Object messageContent = email.getContent();

        if (messageContent instanceof String) {
            content = (String) messageContent;
        } else if (messageContent instanceof Multipart) {
            Multipart multipart = (Multipart) messageContent;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.getContent() instanceof String) {
                    content += (String) bodyPart.getContent();
                }
            }
        }

        return content;
    }
}
