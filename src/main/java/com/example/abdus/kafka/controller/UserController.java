package com.example.abdus.kafka.controller;

import com.example.abdus.kafka.model.User;
import com.example.abdus.kafka.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/send")
    public String sendUser(@RequestBody User user) {
        userService.publishUser(user);
        return "User message sent successfully";
    }

}