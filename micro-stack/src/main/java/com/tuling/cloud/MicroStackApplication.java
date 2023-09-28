package com.tuling.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tuling.cloud.mapper")
public class MicroStackApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroStackApplication.class, args);
    }
}
