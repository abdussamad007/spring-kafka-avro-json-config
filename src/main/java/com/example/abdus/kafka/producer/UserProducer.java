package com.example.abdus.kafka.producer;


import com.example.abdus.kafka.config.KafkaProperties;
import com.example.abdus.kafka.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProducer {

    private final KafkaTemplate<String, User> kafkaTemplate;
    private final KafkaProperties config;

    public void sendUser(User user) {
        String topic = config.getTopics().get("userTopic");

        kafkaTemplate.send(topic, user);
        log.info("Sent User to topic {} : {}", topic, user);
    }
}