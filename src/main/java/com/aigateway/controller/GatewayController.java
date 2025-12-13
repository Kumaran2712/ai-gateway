package com.aigateway.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import com.aigateway.service.ApiKeyService;
import com.aigateway.service.LlmService;
import java.util.Map;

@RestController
public class GatewayController {

    private final ApiKeyService apiKeyService;
    private final LlmService llmService;

    public GatewayController(ApiKeyService apiKeyService, LlmService llmService) {
        this.apiKeyService = apiKeyService;
        this.llmService = llmService;
    }

    @PostMapping("/v1/chat/completions")
    public Map<String, Object> chat(
            @RequestHeader("Authorization") String apiKey,
            @RequestBody Map<String, Object> body) {
        
        // Validate API key
        if (!apiKeyService.validateKey(apiKey)) {
            return Map.of("error", "Invalid API key");
        }

        // Extract prompt and generate response
        String prompt = (String) body.get("prompt");
        Map<String, Object> response = llmService.generate(prompt);
        String answer = (String) response.get("response");
        int promptTokens = (int) response.get("prompt_tokens");
        int completionTokens = (int) response.get("completion_tokens");
        int totalTokens = promptTokens + completionTokens;
        
        apiKeyService.useTokens(apiKey, totalTokens);
        
        return Map.of(
            "response", answer,
            "prompt_tokens", promptTokens,
            "completion_tokens", completionTokens,
            "total_tokens", totalTokens
        );
    }

    @GetMapping("/v1/quota")
    public Map<String, Object> getQuota(@RequestHeader("Authorization") String apiKey) {
        return apiKeyService.getQuotaInfo(apiKey);

    }
}