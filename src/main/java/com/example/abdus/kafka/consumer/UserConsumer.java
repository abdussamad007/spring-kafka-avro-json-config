package com.example.abdus.kafka.consumer;

import com.example.abdus.kafka.config.KafkaProperties;
import com.example.abdus.kafka.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserConsumer {

    private final KafkaProperties kafkaProperties;

    @KafkaListener(topics = "#{kafkaProperties.topics['userTopic']}",
            groupId = "#{kafkaProperties.consumer['groupId']}")
    public void listen(User user) {
        log.info("Received Avro User Record: {}", user);
        // Process the user data (e.g., save to DB)
    }
}
