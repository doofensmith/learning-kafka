package com.softlaboratory.auth.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    ResponseEntity<Object> getAllUserPagination(Integer page, Integer size);
    //TODO: create service grant role to user
    //TODO: create service remove role from user
    //TODO: create service set user active

}
