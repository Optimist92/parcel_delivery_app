package com.test.apigateway.filter;

import com.test.apigateway.config.RouterValidator;
import com.test.apigateway.exception.JwtTokenMissingException;
import com.test.apigateway.util.JwtUtil;
import exception.JwtTokenMalformedException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter implements GatewayFilter {

    private final JwtUtil jwtUtil;

    private final RouterValidator routerValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        if (routerValidator.isSecured.test(request)) {
            if (!request.getHeaders().containsKey("Authorization")) {

                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();

            }

            final String token = request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);

            try {
                jwtUtil.validateToken(token);
            } catch (JwtTokenMalformedException | JwtTokenMissingException e) {
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                return response.setComplete();
            }

            Claims claims = jwtUtil.getClaims(token);

            String role = (String) claims.get("Role");
            if (routerValidator.isAdminSecured.test(request) && !role.equals("ROLE_ADMIN")) {
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                log.info("Нужны права администратора");
                return response.setComplete();
            }
            if(routerValidator.isCourierSecured.test(request) && !role.equals("ROLE_COURIER")){
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                log.info("Нужны права курьера");
                return response.setComplete();
            }
            //добавить авторизацию
            //exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
        }

        return chain.filter(exchange);
    }

}