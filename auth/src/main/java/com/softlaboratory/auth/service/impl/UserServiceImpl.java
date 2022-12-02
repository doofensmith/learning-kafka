package com.softlaboratory.auth.service.impl;

import auth.domain.dao.AccountDao;
import auth.repository.AccountRepository;
import com.softlaboratory.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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

}
