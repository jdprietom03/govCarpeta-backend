package com.eafit.backend.shared.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    @Secured("ROLE_USER")
    public String greeting() {
        return "Hola mundo!";
    }   
}
