package com.softlaboratory.auth.service.impl;

import auth.domain.constant.RoleEnum;
import auth.domain.dao.AccountDao;
import auth.domain.dao.RoleDao;
import auth.domain.request.LoginRequest;
import auth.domain.request.RegisterRequest;
import auth.domain.response.LoginResponse;
import auth.repository.AccountRepository;
import auth.repository.RoleRepository;
import basecomponent.utility.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.softlaboratory.auth.kafka.producer.KafkaProducer;
import com.softlaboratory.auth.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import security.util.JwtTokenProvider;

import java.util.Optional;

@Log4j2
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public ResponseEntity<Object> login(LoginRequest request) {
        Optional<AccountDao> account = accountRepository.findByUsernameEquals(request.getUsername());

        if (account.isPresent() && !account.get().getIsActive()) {
            throw new UsernameNotFoundException("Account not found.");
        }

        if (account.isPresent() && !passwordEncoder.matches(request.getPassword(), account.get().getPassword())) {
            throw new BadCredentialsException("Wrong password!");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwt)
                .build();

        return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), loginResponse);
    }

    @Override
    public ResponseEntity<Object> register(RegisterRequest request) throws JsonProcessingException {
        log.info("Starting register request.");
        log.debug("Request body : {}", request);

        log.debug("Find default role.");
        RoleDao role = roleRepository.findByRoleEqualsIgnoreCase(RoleEnum.USER.name());

        try {
            log.debug("Preparing new account data.");
            AccountDao account = new AccountDao();
            account.setUsername(request.getUsername());
            account.setPassword(passwordEncoder.encode(request.getPassword()));

            log.debug("Save new account to repository.");
            account = accountRepository.save(account);

            log.debug("New account id : {}", account.getId());

            log.debug("Send message to broker.");
            kafkaProducer.publishRegisterResponse(account.getId(), request);

        }catch (Exception e) {
            log.info("Register failed.");
            throw e;
        }

        log.info("Register success.");
        return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), null);
    }

    @Override
    public ResponseEntity<Object> validateToken(String token) {
        log.info("Starting validate token.");

        log.debug("Token : {}", token);

        log.debug("Check token expiration.");
        if (tokenProvider.isExpired(token)) {
            log.debug("Token expired.");

            log.info("Token invalid.");
            throw new BadCredentialsException("Invalid token!");
        }else {
            String username = tokenProvider.getUsername(token);
            AccountDao accountDao = accountRepository.getDistinctTopByUsername(username);
            log.debug("Account dao : {}", accountDao);

            log.info("Token valid.");
            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), accountDao);
        }
    }

}
