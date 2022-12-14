package com.softlaboratory.auth.controller;

import auth.domain.request.LoginRequest;
import auth.domain.request.RegisterRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.softlaboratory.auth.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        try {
            return authService.login(request);
        }catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        try {
            return authService.register(request);
        }catch (JsonProcessingException e) {
            throw new RuntimeException();
        }catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(value = "/validate-token")
    public ResponseEntity<Object> validateToken(@RequestBody String token) {
        try {
            return authService.validateToken(token);
        }catch (Exception e) {
            throw e;
        }
    }

}
