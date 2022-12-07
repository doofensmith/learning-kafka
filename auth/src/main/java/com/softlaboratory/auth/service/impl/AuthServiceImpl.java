package com.softlaboratory.auth.service.impl;

import antlr.TokenStreamIOException;
import auth.domain.constant.RoleEnum;
import auth.domain.dao.AccountDao;
import auth.domain.dao.ProfileDao;
import auth.domain.dao.RoleDao;
import auth.domain.request.LoginRequest;
import auth.domain.request.RegisterRequest;
import auth.domain.response.LoginResponse;
import auth.repository.AccountRepository;
import auth.repository.ProfileRepository;
import auth.repository.RoleRepository;
import basecomponent.common.ApiResponse;
import basecomponent.utility.ResponseUtil;
import com.softlaboratory.auth.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
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
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import security.util.JwtTokenProvider;

import java.util.Optional;

@Log4j2
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

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
    public ResponseEntity<Object> register(RegisterRequest request) {
        RoleDao role = roleRepository.findByRoleEqualsIgnoreCase(RoleEnum.USER.name());

        ProfileDao profileDao = new ProfileDao();
        profileDao.setFullname(request.getFullname());

        profileDao = profileRepository.save(profileDao);

        try {
            AccountDao account = new AccountDao();
            account.setProfile(profileDao);
            account.setUsername(request.getUsername());
            account.setPassword(passwordEncoder.encode(request.getPassword()));

            account = accountRepository.save(account);
        }catch (Exception e) {
            profileRepository.delete(profileDao);
            throw e;
        }

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

    @Override
    public ResponseEntity<Object> test() {
        WebClient webClient = WebClient.create("http://localhost:5001");
        LoginRequest request = LoginRequest.builder()
                .username("admin1")
                .password("admin1")
                .build();

        ApiResponse response = webClient.post()
                .uri("/api/auth/login")
                .body(Mono.just(request), LoginRequest.class)
                .retrieve()
                .bodyToFlux(ApiResponse.class).blockFirst();

        return new ResponseEntity<>(response, HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
    }
}
