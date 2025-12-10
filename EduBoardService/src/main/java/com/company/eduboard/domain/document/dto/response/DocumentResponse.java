package com.company.eduboard.domain.document.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DocumentResponse {
    private final Long documentId;
    private final String title;
    private final String content;
    private final String author;
    private final String createdDate;
    private final Long lockVersion;

    public static DocumentResponse from(Long documentId, String title, String content, String author, String createdDate, Long lockVersion){
        return DocumentResponse.builder()
                .documentId(documentId)
                .title(title)
                .content(content)
                .author(author)
                .createdDate(createdDate)
                .lockVersion(lockVersion)
                .build();
    }

    public static DocumentResponse from(Long documentId, String title, String content, Long lockVersion){
        return DocumentResponse.builder()
                .documentId(documentId)
                .title(title)
                .content(content)
                .lockVersion(lockVersion)
                .build();
    }
}
