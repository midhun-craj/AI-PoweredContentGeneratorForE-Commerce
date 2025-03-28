package com.mcr.gatewayservice.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    private static final Set<String> openApiEndPoints =
            Set.of(
                    "/api/auth/login",
                    "/api/auth/register",
                    "/actuator/health"
            );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();

        if(isOpenApiEndpoint(path)) {

            if(path.startsWith("/actuator/health")) {

                String remoteIp = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");

                if(remoteIp == null) {
                    InetSocketAddress socketAddress = exchange.getRequest().getRemoteAddress();
                    remoteIp = (socketAddress != null && socketAddress.getAddress() != null)
                            ? socketAddress.getAddress().getHostAddress()
                            : null;

                } else {
                    remoteIp = remoteIp.split(",")[0].trim();
                }

                if(!("127.0.0.1".equals(remoteIp) || "0:0:0:0:0:0:0:1".equals(remoteIp) || "::1".equals(remoteIp))) {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
            }
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        Claims claims;
        try {
            claims = jwtUtil.extractAllClaims(token);
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String userId = claims.get("userId", String.class);
        String email = claims.getSubject();
        String username = claims.get("username", String.class);
        String role = claims.get("role", String.class);

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("X-User-Id", userId != null ? userId : "")
                .header("X-Email", email != null ? email : "")
                .header("X-Username", username != null ? username : "")
                .header("X-User-Role", role != null ? role : "")
                .build();

        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

        return chain.filter(mutatedExchange);
    }

    public boolean isOpenApiEndpoint(String path) {
        return openApiEndPoints.stream().anyMatch(path::startsWith);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
