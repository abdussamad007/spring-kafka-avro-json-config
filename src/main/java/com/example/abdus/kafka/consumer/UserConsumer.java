package com.example.abdus.kafka.consumer;

import com.example.abdus.kafka.model.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {

    @KafkaListener(topics = "users-avro", groupId = "user-consumer-group")
    public void consume(User user) {
        System.out.println("Consumed User: " + user);
    }
}
