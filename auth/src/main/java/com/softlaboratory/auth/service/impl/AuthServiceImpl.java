package com.softlaboratory.auth.service.impl;

import auth.domain.request.LoginRequest;
import auth.domain.request.RegisterRequest;
import auth.repository.AccountRepository;
import auth.repository.ProfileRepository;
import com.softlaboratory.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class AuthServiceImpl implements AuthService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public ResponseEntity<Object> login(LoginRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> register(RegisterRequest request) {
        return null;
    }
}
