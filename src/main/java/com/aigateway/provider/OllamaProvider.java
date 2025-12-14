package com.aigateway.provider;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.Map;

@Service
public class OllamaProvider implements LlmProvider {

    private final RestTemplate restTemplate;

    public OllamaProvider() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String getName() {
        return "ollama";
    }

    @Override
    public Map<String, Object> generate(String prompt) {
        String url = "http://localhost:11434/api/generate";
        
        Map<String, Object> requestBody = Map.of(
            "model", "llama3.2:1b",
            "prompt", prompt,
            "stream", false
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);

        return Map.of(
            "response", response.get("response"),
            "prompt_tokens", response.get("prompt_eval_count"),
            "completion_tokens", response.get("eval_count")
        );
    }
}
