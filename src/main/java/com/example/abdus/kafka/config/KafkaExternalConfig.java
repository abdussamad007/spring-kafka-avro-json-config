package com.example.abdus.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.Map;

@Configuration
public class KafkaExternalConfig {

    @Bean("kafkaConfig")
    public Map<String, Object> loadKafkaConfig() throws Exception {
        InputStream input = getClass().getClassLoader().getResourceAsStream("kafkaConfig.json");
        return new ObjectMapper().readValue(input, Map.class);
    }
}
