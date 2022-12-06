package security.filter;

import basecomponent.constant.AppConstant;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import security.util.JwtTokenProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class ClientSecurityFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = null;
            String username = null;

            if (authorization != null && authorization.startsWith(AppConstant.TOKEN_PREFIX)) {
                token = authorization.replace(AppConstant.TOKEN_PREFIX, "");
                try {
                    username = tokenProvider.getUsername(token);
                }catch (Exception e) {
                    throw e;
                }

                Authentication authentication = null;
            }


            filterChain.doFilter(request, response);
        }catch (Exception e) {
            throw e;
        }
    }

}
