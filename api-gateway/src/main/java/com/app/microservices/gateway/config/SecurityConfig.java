package com.app.microservices.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final String[] publicUrls = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resource/**",
            "/aggregate/**",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/actuator/prometheus"
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
        return httpSecurity.authorizeHttpRequests(authorize-> authorize
                        .requestMatchers(publicUrls).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2-> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }
}
