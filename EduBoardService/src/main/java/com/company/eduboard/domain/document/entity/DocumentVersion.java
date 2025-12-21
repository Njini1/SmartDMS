package com.company.eduboard.domain.document.entity;

import com.company.eduboard.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long versionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    private Long versionNumber; // 버전 번호, 1, 2, 3, ...

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contentText;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contentSignature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = false)
    private User updatedBy;

    @CreatedDate
    private LocalDateTime updatedDate;

    @Column(length = 50)
    private String changeReason; // 변경 사유

    @Builder
    public DocumentVersion(Document document, Long versionNumber, String content, String contentText, String contentSignature, User updatedBy, String changeReason) {
        this.document = document;
        this.versionNumber = versionNumber;
        this.content = content;
        this.contentText = contentText;
        this.contentSignature = contentSignature;
        this.updatedBy = updatedBy;
        this.changeReason = changeReason;
    }

    public static DocumentVersion of(Document document, Long versionNumber, String content, String contentText, String contentSignature, User updatedBy, String changeReason) {
        return DocumentVersion.builder()
                .document(document)
                .versionNumber(versionNumber)
                .content(content)
                .contentText(contentText)
                .contentSignature(contentSignature)
                .updatedBy(updatedBy)
                .changeReason(changeReason == null ? "최초 등록" : changeReason)
                .build();
    }
}
