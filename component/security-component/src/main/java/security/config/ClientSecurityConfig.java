package security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.web.reactive.function.client.WebClient;

public class ClientSecurityConfig extends WebSecurityConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:5001/api/auth")
                .build();
    }

}
