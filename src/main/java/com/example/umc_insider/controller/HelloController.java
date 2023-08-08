package com.example.umc_insider.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

/* 테스트용 */

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello World!";
    }
}
