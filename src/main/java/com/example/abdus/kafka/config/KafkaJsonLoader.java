package com.example.abdus.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaJsonLoader {

    private final KafkaProperties kafkaProperties;
    private final ResourceLoader resourceLoader;

    @PostConstruct
    public void loadJsonConfig() {
        try {
            InputStream input = resourceLoader
                    .getResource(kafkaProperties.getConfigFile())
                    .getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            // Read the JSON file into a temporary instance
            KafkaProperties loaded = mapper.readValue(input, KafkaProperties.class);

            // Update the existing Spring-managed bean fields
            kafkaProperties.setBootstrapServers(loaded.getBootstrapServers());
            kafkaProperties.setSchemaRegistryUrl(loaded.getSchemaRegistryUrl());
            kafkaProperties.setProducer(loaded.getProducer());
            kafkaProperties.setConsumer(loaded.getConsumer());
            kafkaProperties.setTopics(loaded.getTopics());

            log.info("Kafka JSON configuration loaded successfully!");

        } catch (Exception e) {
            log.error("Failed to load KafkaConfig.json", e);
            throw new RuntimeException("Failed to load KafkaConfig.json", e);
        }
    }
}