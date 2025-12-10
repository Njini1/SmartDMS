package com.company.eduboard.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 공통 / 서버 에러
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SYS_500", "정의되지 않은 서버 오류가 발생하였습니다."),

    // 인증 / 인가 관련
    AUTH_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_401", "로그인이 필요합니다."),
    AUTH_FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH_403", "해당 자원에 접근할 권한이 없습니다."),

    // 사용자 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_404", "해당 ID에 해당하는 사용자가 없습니다."),
    USER_NOT_FOUND_EMAIL(HttpStatus.UNAUTHORIZED, "USER_401", "해당 이메일과 일치하는 사용자가 없습니다."),
    USER_DUPLICATE_EMAIL(HttpStatus.CONFLICT, "USER_409", "이미 사용 중인 이메일입니다."),
    USER_INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "USER_401", "비밀번호가 일치하지 않습니다."),

    // 게시물 관련
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD_404", "해당하는 게시물이 없습니다."),

    // 댓글 관련
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_404", "해당하는 댓글이 없습니다."),

    // 문서 관련
    DOCUMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "DOC_404", "해당하는 문서가 없습니다."),
    DOCUMENT_VERSION_NOT_FOUND(HttpStatus.NOT_FOUND, "DOC_VER_404", "해당하는 문서 버전이 없습니다."),
    DOCUMENT_VERSION_CONFLICT(HttpStatus.CONFLICT, "DOC_409", "문서 버전 충돌이 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
