package com.softlaboratory.product.exception;

import org.flywaydb.core.api.ErrorDetails;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class SecurityErrorResolver implements ErrorController {

    @RequestMapping(value = "/error")
    public ResponseEntity<ErrorDetails> resolveError(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        throw (Throwable) request.getAttribute("javax.servlet.error.exception");
    }

    public String getErrorPath() {
        return "/error";
    }

}
