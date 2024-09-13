package com.emailSenderWithChat.emailSenderWithChat.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.emailSenderWithChat.emailSenderWithChat.business.abstracts.MessageService;
import com.emailSenderWithChat.emailSenderWithChat.business.request.ChatGptRequest;
import com.emailSenderWithChat.emailSenderWithChat.business.response.ChatGptResponse;

@Service
public class MessageManager implements MessageService {

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    @Autowired
    public MessageManager(RestTemplate restTemplate, HttpHeaders openAiHeaders) {
        this.restTemplate = restTemplate;
        this.headers = openAiHeaders;
    }

    @Override
    public ChatGptResponse analyzeMessage(String message) {
        String apiUrl = "https://api.openai.com/v1/completions";
        ChatGptRequest request = new ChatGptRequest("gpt-3.5-turbo", message, 150, 0.7);
        HttpEntity<ChatGptRequest> entity = new HttpEntity<>(request, headers);

        int retryCount = 0;
        int maxRetries = 3;  
        int delay = 2000;    

        while (retryCount < maxRetries) {
            try {
                return restTemplate.exchange(apiUrl, HttpMethod.POST, entity, ChatGptResponse.class).getBody();
            } catch (HttpClientErrorException.TooManyRequests e) {
                retryCount++;
                if (retryCount < maxRetries) {
                    try {
                        Thread.sleep(delay);  
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();  
                    }
                } else {
                    return new ChatGptResponse(null, "error", null, "gpt-3.5-turbo", "Exceeded retry attempts: " + e.getMessage());
                }
            } catch (HttpClientErrorException e) {
                return new ChatGptResponse(null, "error", null, "gpt-3.5-turbo", "Error while calling AI API: " + e.getMessage());
            }
        }
        return null;
    }

}
