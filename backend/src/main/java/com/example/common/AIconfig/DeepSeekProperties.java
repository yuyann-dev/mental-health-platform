package com.example.common.AIconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "deepseek")
@Data
public class DeepSeekProperties {
    private String apiUrl;
    private String apiKey;
    private String model;
}
