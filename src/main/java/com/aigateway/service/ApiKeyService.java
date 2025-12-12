package com.aigateway.service;

import org.springframework.stereotype.Service;
import com.aigateway.repository.ApiKeyRepository;
import com.aigateway.model.ApiKey;
import java.util.Optional;

@Service
public class ApiKeyService {
    
    private final ApiKeyRepository apiKeyRepository;
    
    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public boolean validateKey(String key) {
        Optional<ApiKey> apiKey = apiKeyRepository.findByKey(key);
        
        if (apiKey.isPresent()) {
            ApiKey actualKey = apiKey.get();
            return actualKey.getUsedTokens() < actualKey.getQuotaLimit();
        }
        
        return false;
    }

    public void useTokens(String key, Integer tokens) {
        Optional<ApiKey> apiKey = apiKeyRepository.findByKey(key);
        
        if (apiKey.isPresent()) {
            ApiKey actualKey = apiKey.get();
            actualKey.setUsedTokens(actualKey.getUsedTokens() + tokens);
            apiKeyRepository.save(actualKey);
        }
    }
}