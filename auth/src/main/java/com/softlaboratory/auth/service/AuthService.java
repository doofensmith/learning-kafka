package com.softlaboratory.auth.service;

import auth.domain.dao.AccountDao;
import auth.domain.request.LoginRequest;
import auth.domain.request.RegisterRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    ResponseEntity<Object> login(LoginRequest request);
    ResponseEntity<Object> register(RegisterRequest request) throws JsonProcessingException;
    ResponseEntity<Object> validateToken(String token);

}
