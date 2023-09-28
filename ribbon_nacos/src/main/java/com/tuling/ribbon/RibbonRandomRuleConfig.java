package com.tuling.ribbon;


import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * 定义一个返回 IRule 的配置类bean, 用于实现对某个服务修改默认的负载均衡策略
 * ---------------------------------------------------------------------
 *
 * 配置一个基本的策略用于某个单独的服务，需要独立于全局ComponentScan扫描
 * 范围之外。否则这个规则将被全局应用
 * ---------------------------------------------------------------------
 */
@Configuration
public class RibbonRandomRuleConfig {

    // 方法的返回值与方法名都是固定的
    @Bean
    public IRule iRule(){
        return new RandomRule();
    }

}
