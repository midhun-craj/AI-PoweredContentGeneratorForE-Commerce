package com.mcr.aicontentservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/ai/**").permitAll()
                        .requestMatchers("/actuator/health")
                        .access((authentication, context) -> {
                            String remoteAddr = context.getRequest().getRemoteAddr();
                            return new AuthorizationDecision(
                                    remoteAddr.equals("127.0.0.1") || remoteAddr.startsWith("172.")
                            );
                        })
                        .anyRequest().denyAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
