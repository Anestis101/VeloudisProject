package com.api.gateway;
import com.api.Filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

@Configuration
public class ApiGatewayConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public ApiGatewayConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public WebFilter globalFilter() {
        return jwtAuthenticationFilter;
    }
}
