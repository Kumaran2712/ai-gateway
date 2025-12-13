package com.aigateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.Map;

@Service
public class LlmService {

    private final RestTemplate restTemplate;

    public LlmService() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String,Object> generate(String prompt) {
        String url = "http://localhost:11434/api/generate";
        
        // Create request body
        Map<String, Object> requestBody = Map.of(
            "model", "llama3.2:1b",
            "prompt", prompt,
            "stream", false
        );

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        // Send request and get response
        Map<String,Object> response = restTemplate.postForObject(url, request, Map.class);

        // Extract the generated text
        return Map.of(
            "response", response.get("response"),
            "prompt_tokens", response.get("prompt_eval_count"),
            "completion_tokens", response.get("eval_count")    
        );
    }
}