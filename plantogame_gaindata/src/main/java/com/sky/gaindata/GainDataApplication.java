package com.sky.gaindata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import utils.IdWorker;
import utils.JwtUtil;

import java.util.TimeZone;

@SpringBootApplication
@EnableEurekaClient
public class GainDataApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(GainDataApplication.class);
    }
    @Bean
    public IdWorker idWorkker() {
        return new IdWorker(1, 1);
    }

    //安全工具
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //加密工具
    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

}

