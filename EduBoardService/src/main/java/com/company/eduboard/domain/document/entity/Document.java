package com.company.eduboard.domain.document.entity;

import com.company.eduboard.domain.user.entity.User;
import com.company.eduboard.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "document")
public class Document extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @Column(nullable = false, length = 200)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentStatus status = DocumentStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // optimistic locking : 수정될 때마다 자동으로 버전이 증가 -> 동시성 처리
    @Version
    private Long lockVersion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_version_id")
    private DocumentVersion currentVersion;

    // 보존 연한 필드 추가 -> 폐기관리용

    @Builder
    public Document(String title, User owner, DocumentVersion currentVersion) {
        this.title = title;
        this.owner = owner;
        this.currentVersion = currentVersion;
    }

    public static Document of(String title, User user) {
        return Document.builder()
                .title(title)
                .owner(user)
                .build();
    }

    // 현재 버전 업데이트(현재 버전이 없으면 1부터 시작)
    public Long updateNextVersionNumber() {
        if (currentVersion == null) {
            return 1L;
        }
        return currentVersion.getVersionNumber() + 1;
    }

    public void updateCurrentVersionAndTitle(DocumentVersion newVersion, String newTitle) {
        this.currentVersion = newVersion;
        this.title = newTitle;
    }
}
