package com.tuling.clond;

import com.tuling.ribbon.RibbonRandomRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
/* 基于配置类的方式配置某个服务远程调用时候的负载均衡策略 */
//@RibbonClients(value = {
//        @RibbonClient(name="stack-service", configuration = {RibbonRandomRuleConfig.class})
//})
public class RibbonNacosApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    public static void main(String[] args) {
        SpringApplication.run(RibbonNacosApplication.class, args);
    }

}
