package com.tuling.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroUserApplication.class, args);
    }
}