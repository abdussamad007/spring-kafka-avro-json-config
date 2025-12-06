package com.example.abdus.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component // Must be a Spring Bean
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    // Loaded from application.yml: kafka.config-file
    private String configFile;

    // These fields are populated by KafkaJsonLoader from KafkaConfig.json
    private String bootstrapServers;
    private String schemaRegistryUrl;
    private Map<String, Object> producer; // Holds key.serializer, value.serializer, acks, etc.
    private Map<String, Object> consumer; // Holds group.id, key.deserializer, value.deserializer, specificAvroReader, etc.
    private Map<String, String> topics;   // Holds topic name mapping
}