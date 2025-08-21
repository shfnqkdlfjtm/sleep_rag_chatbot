package com.example.sleep.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String,String>> onAny(Exception e){
    return ResponseEntity.status(500).body(Map.of(
        "error","오류가 발생했습니다: "+e.getMessage(),
        "action","다시 시도해주세요"
    ));
  }
}
