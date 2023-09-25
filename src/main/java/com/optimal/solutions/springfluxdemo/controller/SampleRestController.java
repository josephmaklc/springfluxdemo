package com.optimal.solutions.springfluxdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class SampleRestController {

    @GetMapping("test")
    public Mono<String> testing123(final @RequestParam String name) {

        return Mono.just("Hello my friend "+name);
    }

}