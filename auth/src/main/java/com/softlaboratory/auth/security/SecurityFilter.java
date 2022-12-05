package com.softlaboratory.auth.security;

import auth.domain.dao.AccountDao;
import basecomponent.constant.AppConstant;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@AllArgsConstructor
public class SecurityFilter extends GenericFilterBean {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider tokenProvider;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

            String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
            String token = null;
            String username = null;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authorization != null && authorization.startsWith(AppConstant.TOKEN_PREFIX)) {
                token = authorization.replace(AppConstant.TOKEN_PREFIX,"");
                try {
                    username = tokenProvider.getUsername(token);
                }catch (Exception e) {
                    throw e;
                }
            }

            if (username != null && authentication == null) {
                AccountDao account = (AccountDao) userDetailsService.loadUserByUsername(username);

                if (tokenProvider.isTokenValid(token, account)) {
                    Authentication authenticationToken = tokenProvider.getAuthenticationToken(token, authentication, account);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(servletRequest, servletResponse);
        }catch (Exception e) {
            throw e;
        }
    }
}
