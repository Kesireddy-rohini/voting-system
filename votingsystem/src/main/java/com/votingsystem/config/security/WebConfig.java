package com.votingsystem.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all paths
        		.allowedOrigins("http://127.0.0.1:5500") // Frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow necessary methods
                .allowedHeaders("*"); // Allow all headers
    }
}
