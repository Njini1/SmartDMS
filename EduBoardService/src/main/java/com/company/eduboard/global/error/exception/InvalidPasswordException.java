package com.company.eduboard.global.error.exception;

import com.company.eduboard.global.error.AppException;
import com.company.eduboard.global.error.ErrorCode;

public class InvalidPasswordException extends AppException {
    public InvalidPasswordException() {
        super(ErrorCode.USER_INVALID_PASSWORD);
    }
}
