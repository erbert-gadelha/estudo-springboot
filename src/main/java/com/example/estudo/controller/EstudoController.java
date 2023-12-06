package com.example.estudo.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.example.estudo.controller.responses.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstudoController {
    @GetMapping("/")
    public indexResponse index() {
        indexResponse response = new indexResponse("Alô, Brothers!");
        return response;
    }

    @GetMapping("/sqr")
    public sumResponse index(@RequestParam(value = "value", defaultValue = "0") String value) {
        Integer value_;

        try { value_ = Integer.parseInt(value); }
        catch (NumberFormatException e) { value_ = 0; }

        sumResponse response = new sumResponse(value_ * value_);
        return response;
    }
}