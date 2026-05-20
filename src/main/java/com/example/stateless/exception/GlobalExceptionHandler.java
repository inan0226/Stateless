package com.example.stateless.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 우리가 발생시켰던 IllegalArgumentException을 잡아내는 핸들러
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {

        // 에러 발생 시 ERROR 레벨로 스택트레이스를 남깁니다.
        log.error("Exception occurred", e);

        //  클라이언트에게는 깔끔한 에러 메시지와 함께 400 Bad Request 상태 코드를 반환합니다.
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    // 그 외 예상치 못한 모든 예외를 잡는 핸들러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        log.error("Unexpected error occurred", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버 내부에서 오류가 발생했습니다.");
    }
}
