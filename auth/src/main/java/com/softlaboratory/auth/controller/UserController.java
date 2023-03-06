package com.softlaboratory.auth.controller;

import auth.domain.request.GrantRoleRequest;
import basecomponent.query.SearchRequest;
import com.softlaboratory.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user/management")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/user-list")
    public ResponseEntity<Object> getAllUserPagination(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        try {
            return userService.getAllUserPagination(page, size);
        } catch (Exception e) {
            throw e;
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/search-users")
    public ResponseEntity<Object> searchAllUser(@RequestBody SearchRequest request) {
        try {
            return userService.searchAllUser(request);
        } catch (Exception e) {
            throw e;
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/users-stat")
    public ResponseEntity<Object> getUsersInfo() {
        try {
            return userService.getUsersInfo();
        } catch (Exception e) {
            throw e;
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/roles")
    public ResponseEntity<Object> getAllRole() {
        try {
            return userService.getAllRole();
        } catch (Exception e) {
            throw e;
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/grant-role")
    public ResponseEntity<Object> grantRole(@RequestBody GrantRoleRequest request) {
        try {
            return userService.grantRole(request);
        } catch (Exception e) {
            throw e;
        }
    }

}
