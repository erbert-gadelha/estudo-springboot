package com.example.estudo.estudo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeuController {

    @GetMapping("/api/hello")
    public String get() {
        return "Get: Hello, World!";
    }

    @PostMapping("/api/hello")
    public String post() {
        return "Post: Hello, World!";
    }

    @GetMapping("/public/error")
    public String error() {
        return "public/error";
    }

    @GetMapping("/private/success")
    public String success() {
        return "private/success";
    }
}
