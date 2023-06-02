package com.eafit.backend.shared.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eafit.backend.shared.dtos.NewUserDto;
import com.eafit.backend.shared.dtos.UserCredentials;
import com.eafit.backend.shared.services.AuthenticationService;

@RestController
public class SessionController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserCredentials credentials) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(authenticationService.login(credentials));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody NewUserDto newUser) throws NoSuchAlgorithmException {
        authenticationService.register(newUser);
        return ResponseEntity.ok(newUser);
    }
    
}
