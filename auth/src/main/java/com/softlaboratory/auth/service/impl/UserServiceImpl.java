package com.softlaboratory.auth.service.impl;

import auth.domain.dto.UserDto;
import basecomponent.utility.ResponseUtil;
import com.softlaboratory.auth.domain.dao.AccountDao;
import com.softlaboratory.auth.domain.dao.RoleDao;
import com.softlaboratory.auth.repository.AccountRepository;
import com.softlaboratory.auth.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDao account = accountRepository.getDistinctTopByUsername(username);
        if (account!=null) {
            return account;
        }else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

    @Override
    public ResponseEntity<Object> getAllUserPagination(Integer page, Integer size) {
        log.debug("Starting get all user with page.");
        log.debug("Request page : {}, size : {}.", page, size);

        log.debug("Default page and size.");
        Pageable pageable = PageRequest.of(
                Objects.requireNonNullElse(page, 0),
                Objects.requireNonNullElse(size, 10)
        );

        log.debug("Fetch list user with repository.");
        Page<AccountDao> accounts = accountRepository.findAll(pageable);

        log.debug("Convert to data presentation.");
        List<UserDto> users =new ArrayList<>();
        accounts.forEach(accountDao -> {
            List<String> roles = new ArrayList<>();
            accountDao.getRoles().forEach(roleDao -> roles.add(roleDao.getRole()));
            users.add(
                    UserDto.builder()
                            .id(accountDao.getId())
                            .username(accountDao.getUsername())
                            .isActive(accountDao.getIsActive())
                            .loginAttemp(accountDao.getLoginAttemp())
                            .roles(roles)
                            .build()
            );
        });

        log.debug("Get all users pagination success.");
        return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), users);
    }
}
