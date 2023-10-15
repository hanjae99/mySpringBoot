package com.keduit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 들어올 수 있는 허용 경로
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // 해당 경로가 사용할수 있는 메소드
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS); // 허용 시간
    }
}
