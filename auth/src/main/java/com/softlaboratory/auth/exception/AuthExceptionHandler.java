package com.softlaboratory.auth.exception;

import basecomponent.utility.ResponseUtil;
import com.softlaboratory.auth.controller.AuthController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {AuthController.class})
public class AuthExceptionHandler {

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentials(Exception e) {
        return ResponseUtil.build(HttpStatus.UNAUTHORIZED, "(Bad Credentials) Wrong Password!", null);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFound(Exception e) {
        return ResponseUtil.build(HttpStatus.UNAUTHORIZED, "(Bad Credentials) User not found!", null);
    }

}
