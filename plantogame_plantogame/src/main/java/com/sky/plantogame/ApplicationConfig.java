package com.sky.plantogame;

import com.sky.plantogame.interceptor.IpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {
    private final IpInterceptor ipInterceptor;

    @Autowired
    public ApplicationConfig(IpInterceptor ipInterceptor) {
        this.ipInterceptor = ipInterceptor;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipInterceptor).addPathPatterns("/**");
    }
}
