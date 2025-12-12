package com.aigateway.repository;

import com.aigateway.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long>{

    Optional <ApiKey> findByKey(String Key);
    
}
