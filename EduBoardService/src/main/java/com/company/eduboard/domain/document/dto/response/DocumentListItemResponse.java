package com.company.eduboard.domain.document.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DocumentListItemResponse {
    private Long documentId;
    private String title;
    private String ownerName;
    private Long currentVersionNumber;
    private Long lockVersion;
    private LocalDateTime updatedDate;

    public static DocumentListItemResponse from(Long documentId, String title, String ownerName,
                                                Long currentVersionNumber, Long lockVersion,
                                                LocalDateTime updatedDate) {
        return DocumentListItemResponse.builder()
                .documentId(documentId)
                .title(title)
                .ownerName(ownerName)
                .currentVersionNumber(currentVersionNumber)
                .lockVersion(lockVersion)
                .updatedDate(updatedDate)
                .build();
    }
}