package com.company.eduboard.domain.document.entity;

import com.company.eduboard.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "document_version")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updatedBy", nullable = false)
    private User updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Column(length = 50)
    private String changeReason; // 변경 사유

    // optimistic locking : 수정될 때마다 자동으로 버전이 증가 -> 동시성 처리
    @Version
    private Long lockVersion;

    @Builder
    public DocumentVersion(Document document, Long versionNumber, String content, User updatedBy, String changeReason) {
        this.document = document;
        this.versionNumber = versionNumber;
        this.content = content;
        this.updatedBy = updatedBy;
        this.changeReason = changeReason;
    }
}
