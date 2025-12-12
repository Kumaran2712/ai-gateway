package com.aigateway.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

@RestController
public class HealthController {



        @GetMapping("/health")
        public Map<String,Object> getHealthStatus(){
            return Map.of("status","healthy");
        }
    }
    
