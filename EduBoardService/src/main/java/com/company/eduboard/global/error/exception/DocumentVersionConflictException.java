package com.company.eduboard.global.error.exception;

public class DocumentVersionConflictException extends RuntimeException{
    public DocumentVersionConflictException(String message) {
        super(message);
    }
}
