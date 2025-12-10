package com.company.eduboard.global.error.exception;

import com.company.eduboard.global.error.AppException;
import com.company.eduboard.global.error.ErrorCode;

// 로그인했지만 다른 사람의 자원에 접근하려 할 때 발생하는 예외
public class UnauthorizedActionException extends AppException {
    public UnauthorizedActionException(String message) {
        super(ErrorCode.AUTH_FORBIDDEN, message);
    }
}
