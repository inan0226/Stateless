package com.example.stateless.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 컨트롤러 로직이 실행되기 전에 호출됩니다.
        // 요구사항 형태: [API - LOG] HTTP메서드 요청URI
        log.info("[API - LOG] {} {}", request.getMethod(), request.getRequestURI());

        // true를 반환해야 다음 단계(컨트롤러)로 정상적으로 넘어갑니다.
        return true;
    }
}
