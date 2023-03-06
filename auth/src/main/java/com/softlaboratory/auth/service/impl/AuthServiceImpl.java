package com.softlaboratory.auth.service.impl;

import auth.domain.request.LoginRequest;
import auth.domain.request.RegisterRequest;
import auth.domain.response.LoginResponse;
import basecomponent.utility.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.softlaboratory.auth.client.CustomerClient;
import com.softlaboratory.auth.domain.dao.AccountDao;
import com.softlaboratory.auth.domain.dao.AccountRolesDao;
import com.softlaboratory.auth.domain.dao.AccountRolesKey;
import com.softlaboratory.auth.domain.dao.RoleDao;
import com.softlaboratory.auth.domain.enums.RoleEnum;
import com.softlaboratory.auth.repository.AccountRepository;
import com.softlaboratory.auth.repository.AccountRolesRepository;
import com.softlaboratory.auth.repository.RoleRepository;
import com.softlaboratory.auth.service.AuthService;
import customer.domain.request.NewProfileRequest;
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
    private AccountRolesRepository accountRolesRepository;

    @Autowired
    private CustomerClient customerClient;

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
        String jwt = tokenProvider.generateToken(account.get().getId(), authentication);

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

            log.debug("Set default role to new account.");
            AccountRolesDao accountRolesDao = new AccountRolesDao();
            accountRolesDao.setId(AccountRolesKey.builder()
                    .idAccount(account.getId())
                    .idRole(role.getId())
                    .build());
            accountRolesRepository.save(accountRolesDao);

            try {
                log.debug("Send account data to create profile to customer service.");
                NewProfileRequest newProfileRequest = NewProfileRequest.builder()
                        .idAccount(account.getId())
                        .fullname(request.getFullname())
                        .profilePic("-")
                        .balance(0D)
                        .point(0D)
                        .build();
                customerClient.newCustomer(newProfileRequest);

            } catch (Exception e) {
                log.info("Register failed.");

                log.debug("Deleting account.");
                accountRepository.delete(account);
                throw e;
            }

        } catch (Exception e) {
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
        } else {
            String username = tokenProvider.getUsername(token);
            AccountDao accountDao = accountRepository.getDistinctTopByUsername(username);
            log.debug("Account dao : {}", accountDao);

            log.info("Token valid.");
            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), accountDao);
        }
    }

}
