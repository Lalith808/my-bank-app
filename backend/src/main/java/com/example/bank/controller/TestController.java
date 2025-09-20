package com.example.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Spring Boot is working! Time: " + java.time.LocalDateTime.now();
    }

    @GetMapping("/api/test")
    public String apiTest() {
        return "API is working! Time: " + java.time.LocalDateTime.now();
    }
}