package com.mcr.aicontentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.mcr.aicontentservice.client")
public class AiContentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiContentServiceApplication.class, args);
    }

}
