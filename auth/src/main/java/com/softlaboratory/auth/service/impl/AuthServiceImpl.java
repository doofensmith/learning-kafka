package com.softlaboratory.auth.service.impl;

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
import basecomponent.utility.ResponseUtil;
import com.softlaboratory.auth.security.JwtTokenProvider;
import com.softlaboratory.auth.service.AuthService;
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

import java.util.Optional;

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
}
