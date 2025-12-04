package com.example.abdus.kafka.producer;

import com.example.abdus.kafka.model.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserProducer {

    private final KafkaTemplate<String, User> kafkaTemplate;
    private final String topic;

    public UserProducer(KafkaTemplate<String, User> kafkaTemplate, Map<String, Object> kafkaConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = ((Map<String, String>) kafkaConfig.get("topics")).get("userTopic");
    }

    public void send(User user) {
        kafkaTemplate.send(topic, user);
        System.out.println("Produced User: " + user);
    }
}
