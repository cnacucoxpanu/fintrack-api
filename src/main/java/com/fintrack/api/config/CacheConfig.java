package com.fintrack.api.config;

import com.fintrack.api.dto.SearchKey;
import com.fintrack.api.dto.TransactionDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CacheConfig {

    @Bean
    public Map<SearchKey, Page<TransactionDto>> transactionCache() {
        return new HashMap<>();
    }
}
