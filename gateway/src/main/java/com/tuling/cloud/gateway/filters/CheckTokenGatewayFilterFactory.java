package com.tuling.cloud.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class CheckTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<CheckTokenGatewayFilterFactory.Config> {

    public CheckTokenGatewayFilterFactory() {
        super(Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return Arrays.asList("token");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (ServerWebExchange exchange, GatewayFilterChain chain) -> {
            if (exchange.getRequest().getHeaders().containsKey("token")) {
                String token = exchange.getRequest().getHeaders().getFirst("token");
                if (!StringUtils.isEmpty(token) && Objects.equals(token, config.token)) {
                    log.info("token is valid");
                    return chain.filter(exchange);
                } else {
                    log.info("token is invalid");
                    exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                    exchange.getResponse().getHeaders().add("Content-Type", "text/plain; charset=UTF-8");
                    return exchange.getResponse().writeWith(
                        Mono.just(
                            exchange.getResponse().bufferFactory().wrap(
                                "token is invalid".getBytes()
                            )
                        )
                    );
                }
            }
            log.info("token is empty");
            return Mono.empty();
        };
    }

    public static class Config {

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
