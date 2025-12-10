package com.company.eduboard.global.error.exception;

import com.company.eduboard.global.error.AppException;
import com.company.eduboard.global.error.ErrorCode;

public class DocumentVersionConflictException extends AppException {
    public DocumentVersionConflictException() {
        super(ErrorCode.DOCUMENT_VERSION_CONFLICT);
    }

    public DocumentVersionConflictException(long documentId) {
        super(ErrorCode.DOCUMENT_VERSION_CONFLICT, "문서 버전 충돌이 발생했습니다. documentId: " + documentId);
    }


}
