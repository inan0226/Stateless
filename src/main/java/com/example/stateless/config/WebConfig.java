package com.example.stateless.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 인터셉터를 등록하고, '/api/**' 경로로 들어오는 요청에만 적용하도록 설정합니다.
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/api/**");
    }
}