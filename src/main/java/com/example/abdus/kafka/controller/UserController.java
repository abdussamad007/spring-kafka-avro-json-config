package com.example.abdus.kafka.controller;

import com.example.abdus.kafka.model.User;
import com.example.abdus.kafka.producer.UserProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserProducer producer;

    public UserController(UserProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String send(@RequestBody User user) {
        producer.send(user);
        return "User sent!";
    }
}
