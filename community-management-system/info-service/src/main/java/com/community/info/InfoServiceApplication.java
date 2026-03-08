package com.community.info;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.community"})
@MapperScan(basePackages = {"com.community.info.mapper"})
public class InfoServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfoServiceApplication.class, args);
    }
}