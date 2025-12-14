package com.aigateway.service;

import org.springframework.stereotype.Service;
import com.aigateway.provider.LlmProvider;
import com.aigateway.provider.OllamaProvider;
import com.aigateway.provider.OpenAIProvider;
import java.util.Map;
import java.util.HashMap;

@Service
public class LlmService {

    private final OllamaProvider ollamaProvider;
    private final OpenAIProvider openAIProvider;

    public LlmService(OllamaProvider ollamaProvider, OpenAIProvider openAIProvider) {
        this.ollamaProvider = ollamaProvider;
        this.openAIProvider = openAIProvider;
    }

    public Map<String, Object> generate(String prompt) {
        return generate(prompt, "ollama");
    }

    public Map<String, Object> generate(String prompt, String provider) {
        LlmProvider selectedProvider = switch (provider.toLowerCase()) {
            case "openai" -> openAIProvider;
            case "ollama" -> ollamaProvider;
            default -> ollamaProvider;
        };

        Map<String, Object> response = selectedProvider.generate(prompt);
        response = new HashMap<>(response);
        response.put("provider", selectedProvider.getName());
        return response;
    }
}
