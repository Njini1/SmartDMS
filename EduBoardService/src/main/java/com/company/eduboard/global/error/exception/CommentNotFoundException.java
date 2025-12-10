package com.company.eduboard.global.error.exception;

import com.company.eduboard.global.error.AppException;
import com.company.eduboard.global.error.ErrorCode;

public class CommentNotFoundException extends AppException {
    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND, "해당 댓글을 찾을 수 없습니다.");
    }

    public CommentNotFoundException(int commentId) {
        super(ErrorCode.COMMENT_NOT_FOUND, "해당 댓글을 찾을 수 없습니다. ID: " + commentId);
    }
}
