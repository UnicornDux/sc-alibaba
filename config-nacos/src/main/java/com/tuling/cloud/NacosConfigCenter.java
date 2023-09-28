package com.tuling.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosConfigCenter {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(NacosConfigCenter.class, args);
        while(true) {
            String appName = applicationContext.getEnvironment().getProperty("redis.username");
            String areaName = applicationContext.getEnvironment().getProperty("user.area");
            System.out.println("appName:" + appName);
            System.out.println("areaName:" + areaName);
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
