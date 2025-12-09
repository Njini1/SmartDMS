package com.company.eduboard.domain.document.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentUpdateRequest {
    private Long documentId;
    private Long lockVersion;
    private String newTitle;
    private String newContent;
    private String changeReason;
}
