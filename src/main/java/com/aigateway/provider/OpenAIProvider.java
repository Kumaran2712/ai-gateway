package com.aigateway.provider;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIProvider implements LlmProvider {

    private final RestTemplate restTemplate;
    private final String apiKey;

    public OpenAIProvider() {
        this.restTemplate = new RestTemplate();
        this.apiKey = System.getenv("OPENAI_API_KEY");
    }

    @Override
    public String getName() {
        return "openai";
    }

    @Override
    public Map<String, Object> generate(String prompt) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("OPENAI_API_KEY environment variable not set");
        }
        
        String url = "https://api.openai.com/v1/chat/completions";
        
        Map<String, Object> requestBody = Map.of(
            "model", "gpt-3.5-turbo",
            "messages", List.of(
                Map.of("role", "user", "content", prompt)
            )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, request, Map.class);

        Map<String, Object> body = responseEntity.getBody();
        if (body == null) {
            throw new RuntimeException("Empty response from OpenAI API");
        }
        
        List<Map<String, Object>> choices = (List<Map<String, Object>>) body.get("choices");
        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
        String content = (String) message.get("content");
        Map<String, Object> usage = (Map<String, Object>) body.get("usage");
        
        return Map.of(
            "response", content,
            "prompt_tokens", usage.get("prompt_tokens"),
            "completion_tokens", usage.get("completion_tokens")
        );
    }
}
