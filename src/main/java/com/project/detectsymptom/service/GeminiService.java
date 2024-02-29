package com.project.detectsymptom.service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class GeminiService {

    private static final String API_KEY = "AIzaSyDx2Vq_FGhvCjpVfa9WTVQJSq62YTMs3-k";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent";



    public CompletableFuture<String> generateContent(String prompt) {
        return CompletableFuture.supplyAsync(() -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = "{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}";

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> responseEntity = new RestTemplate().exchange(API_URL + "?key=" + API_KEY,
                    HttpMethod.POST, requestEntity, String.class);

            HttpStatus statusCode = responseEntity.getStatusCode();
            String responseBody = responseEntity.getBody();

            System.out.println("Status Code: " + statusCode);

            // Hi
            return responseBody;
        });
    }

}

