package com.tuling.clond.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 *  自定义负载均衡策略
 *  ------------------------------------------------------
 *  1、继承AbstractLoadBalancerRule 类，复写如下两个方法
 *  2、将当前策略配置在配置文件中，或配置一个配置类
 */
public class CustomRule extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        ILoadBalancer loadBalancer = this.getLoadBalancer();
        // 获取当前的请求的服务的实例
        List<Server> reachServers = loadBalancer.getAllServers();

        int random = ThreadLocalRandom.current().nextInt(reachServers.size());

        return reachServers.get(random);
    }
}
