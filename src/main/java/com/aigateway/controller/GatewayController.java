package com.aigateway.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import com.aigateway.service.ApiKeyService;
import java.util.Map;

@RestController
public class GatewayController {

    private final ApiKeyService apiKeyService;

    public GatewayController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @PostMapping("/v1/chat/completions")
    public Map<String, Object> validateApi(@RequestHeader("Authorization") String apiKey) {
        boolean isValid = apiKeyService.validateKey(apiKey);

        if (!isValid) {
            return Map.of("error", "Invalid API key");
        }

        return Map.of("status", "success");
    }
}