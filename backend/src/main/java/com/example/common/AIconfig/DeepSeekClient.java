package com.example.common.AIconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.function.Consumer;

@Service
public class DeepSeekClient {

    @Autowired
    private DeepSeekProperties deepSeekProperties;

    @Autowired
    private RestTemplate restTemplate;

    public String sendMessage(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + deepSeekProperties.getApiKey());

        DeepSeekRequest request = new DeepSeekRequest();
        request.setModel(deepSeekProperties.getModel());
        DeepSeekRequest.Message message = new DeepSeekRequest.Message("user", prompt);
        request.getMessages().add(message);

        HttpEntity<DeepSeekRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    deepSeekProperties.getApiUrl(),
                HttpMethod.POST,
                entity,
                String.class
            );
            if (response.getBody() != null) {
                return response.getBody();
            } else {
                return "No response from DeepSeek";
            }
        } catch (Exception e) {
            return "Error calling DeepSeek API: " + e.getMessage();
        }
    }


    public void sendMessageStream(String prompt, ResponseExtractor<Void> responseExtractor, Consumer<Exception> consumer) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");
        headers.set("Authorization", "Bearer " + deepSeekProperties.getApiKey());
        headers.setAccept(Collections.singletonList(MediaType.TEXT_EVENT_STREAM));

        DeepSeekRequest request = new DeepSeekRequest();
        request.setModel(deepSeekProperties.getModel());
        DeepSeekRequest.Message message = new DeepSeekRequest.Message("user", prompt);
        request.getMessages().add(message);

        HttpEntity<DeepSeekRequest> entity = new HttpEntity<>(request, headers);

        // 使用 RestTemplate 进行流式请求
        RequestCallback requestCallback = restTemplate.httpEntityCallback(entity, DeepSeekRequest.class);

        // 异步执行请求
        new Thread(() -> {
            try {
                restTemplate.execute(deepSeekProperties.getApiUrl(), HttpMethod.POST, requestCallback, responseExtractor);
            } catch (Exception e) {
                consumer.accept(e);
            }
        }).start();
    }
}
