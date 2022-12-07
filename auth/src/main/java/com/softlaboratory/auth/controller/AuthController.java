package com.softlaboratory.auth.controller;

import auth.domain.dao.AccountDao;
import auth.domain.request.LoginRequest;
import auth.domain.request.RegisterRequest;
import auth.domain.response.LoginResponse;
import com.softlaboratory.auth.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/test")
    public ResponseEntity<Object> test() {
        try {
            return authService.test();
        }catch (Exception e) {
            throw e;
        }
    }

}
