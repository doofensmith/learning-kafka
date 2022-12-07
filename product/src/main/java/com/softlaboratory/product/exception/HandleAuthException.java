package com.softlaboratory.product.exception;

import basecomponent.utility.ResponseUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice(assignableTypes = {SecurityErrorResolver.class})
public class HandleAuthException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<Object> jwtExpired(Exception e) {
        //log.trace(e);
        return ResponseUtil.build(HttpStatus.FORBIDDEN, e.getMessage(), null);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> nullPointer(Exception e) {
        //log.trace(e);
        return ResponseUtil.build(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase(), null);
    }

}
