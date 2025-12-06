package com.example.abdus.kafka.service;

import com.example.abdus.kafka.model.User;
import com.example.abdus.kafka.producer.UserProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserProducer userProducer;

    public void publishUser(User user) {
        userProducer.sendUser(user);
    }
}