package security.filter;

import basecomponent.constant.AppConstant;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import security.util.JwtTokenProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.web.reactive.function.client.WebClient.*;


@AllArgsConstructor
public class ClientSecurityFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final WebClient client;

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
            }

            if (username != null) {
                UriSpec<RequestBodySpec> uriSpec = client.method(HttpMethod.POST);
                RequestBodySpec bodySpec = uriSpec.uri("/validate-token");
            }


            filterChain.doFilter(request, response);
        }catch (Exception e) {
            throw e;
        }
    }

}
