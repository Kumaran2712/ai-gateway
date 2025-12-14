package com.aigateway.provider;

import java.util.Map;

public interface LlmProvider {
    String getName();
    Map<String, Object> generate(String prompt);
}
