package com.tuling.cloud.gateway.predicate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@Component
public class CheckAuthRoutePredicateFactory extends AbstractRoutePredicateFactory<CheckAuthRoutePredicateFactory.Config> {

    public CheckAuthRoutePredicateFactory() {
        super(Config.class);
    }

    // 这里返回的是用于接收配置文件中参数的字符串列表，Gateway 框架会根据这些字段
    // 在 Config 中查找对应的 set 方法，进行字段的赋值，最终将配置文件中的值设置
    // 到 Config 类中.
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("name");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return (ServerWebExchange serverWebExchange) -> {
            // 获取参数，需要请求的参数中含有name参数，并且值与我们在配置文件中指定的内容一致，否则不能通过断言
            if(serverWebExchange.getRequest().getQueryParams().containsKey("name")){
                String name = serverWebExchange.getRequest().getQueryParams().getFirst("name");
                if(config.name.equals(name)){
                    log.info(config.getName());
                    return true;
                }
                return false;
            }
            return false;
        };
    }
    @Validated
    public static class Config {
        private String name;

        public String getName() {
            return name;
        }

        public Config setName(String name) {
            this.name = name;
            return this;
        }
    }
}
