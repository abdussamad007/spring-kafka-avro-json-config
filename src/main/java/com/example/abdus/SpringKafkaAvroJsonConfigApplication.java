package com.example.abdus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(exclude = {
        // Crucial step: tells Spring not to use the default Kafka configuration,
        // which caused the "key.deserializer: null" error.
        KafkaAutoConfiguration.class
})
public class SpringKafkaAvroJsonConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaAvroJsonConfigApplication.class, args);
    }
}