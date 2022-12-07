package security.filter;

import auth.domain.dao.AccountDao;
import basecomponent.common.ApiResponse;
import basecomponent.constant.AppConstant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import security.util.JwtTokenProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Log4j2
@AllArgsConstructor
public class ClientSecurityFilter extends GenericFilterBean {

    private final JwtTokenProvider tokenProvider;
    private final WebClient client;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

            String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
            String token = null;
            String username = null;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authorization != null && authorization.startsWith(AppConstant.TOKEN_PREFIX)) {
                token = authorization.replace(AppConstant.TOKEN_PREFIX, "");
                try {
                    username = tokenProvider.getUsername(token);
                }catch (Exception e) {
                    throw e;
                }
            }

            if (username != null && authentication == null && !tokenProvider.isExpired(token)) {
                Authentication authenticationToken = tokenProvider.getAuthenticationTokenClient(token);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(servletRequest, servletResponse);
        }catch (Exception e) {
            throw e;
        }
    }
}
