package com.company.eduboard.global.error.exception;

// 로그인을 안 했거나 접근 권한이 없을 때 발생하는 예외
public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException(String message) {
        super(message);
    }
}
