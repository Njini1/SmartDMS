package com.company.eduboard.global.error.exception;

import com.company.eduboard.global.error.AppException;
import com.company.eduboard.global.error.ErrorCode;

public class BoardNotFoundException extends AppException {

    public BoardNotFoundException() {
        super(ErrorCode.BOARD_NOT_FOUND, "해당 문서를 찾을 수 없습니다.");
    }

    public BoardNotFoundException(int boardId) {
        super(ErrorCode.BOARD_NOT_FOUND, "해당 문서를 찾을 수 없습니다. ID: " + boardId);
    }
}
