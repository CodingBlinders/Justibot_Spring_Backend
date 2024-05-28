package com.justibot.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageService {

    @Value("${external.api.url}")
    private String externalApiUrl;

    private final RestTemplate restTemplate;

    public MessageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String forwardMessage(String chatId, String message) {
        // Create the payload
        Map<String, String> payload = new HashMap<>();
        payload.put("chatId", chatId);
        payload.put("message", message);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(payload, headers);

        // Send the request and get the response
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                externalApiUrl,
//                HttpMethod.POST,
//                requestEntity,
//                String.class
//        );
        //print requestEntity and make a dummy responseEntity
        System.out.println(requestEntity);
        ResponseEntity<String> responseEntity = ResponseEntity.ok("dummy response");

        return responseEntity.getBody();
    }
}
