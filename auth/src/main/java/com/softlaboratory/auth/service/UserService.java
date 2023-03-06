package com.softlaboratory.auth.service;

import auth.domain.request.GrantRoleRequest;
import basecomponent.query.SearchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    ResponseEntity<Object> getAllUserPagination(Integer page, Integer size);

    ResponseEntity<Object> searchAllUser(SearchRequest request);

    ResponseEntity<Object> getUsersInfo();

    ResponseEntity<Object> getAllRole();

    ResponseEntity<Object> grantRole(GrantRoleRequest request);

    ResponseEntity<Object> removeRole();

    ResponseEntity<Object> activateUser();

    ResponseEntity<Object> deactivateUser();

}
