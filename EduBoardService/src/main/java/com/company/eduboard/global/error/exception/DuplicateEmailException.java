package com.company.eduboard.global.error.exception;

import com.company.eduboard.global.error.AppException;
import com.company.eduboard.global.error.ErrorCode;

public class DuplicateEmailException extends AppException {
    public DuplicateEmailException() {
        super(ErrorCode.USER_DUPLICATE_EMAIL);
    }

    public DuplicateEmailException(String email) {
        super(ErrorCode.USER_DUPLICATE_EMAIL, "이미 사용 중인 이메일입니다: " + email);
    }
}
