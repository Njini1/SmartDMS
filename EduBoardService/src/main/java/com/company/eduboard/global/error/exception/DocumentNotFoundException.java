package com.company.eduboard.global.error.exception;

import com.company.eduboard.global.error.AppException;
import com.company.eduboard.global.error.ErrorCode;

public class DocumentNotFoundException extends AppException {
    public DocumentNotFoundException() {
        super(ErrorCode.DOCUMENT_NOT_FOUND);
    }

    public DocumentNotFoundException(long documentId ) {
        super(ErrorCode.DOCUMENT_NOT_FOUND, "해당 문서를 찾을 수 없습니다. ID: " + documentId);
    }
}
