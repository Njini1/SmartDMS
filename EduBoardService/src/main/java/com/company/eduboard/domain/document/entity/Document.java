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

    // 문서 상태 (ACTIVE, ARCHIVED, DISPOSED 등)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentStatus status = DocumentStatus.ACTIVE;

    // 최신 버전 번호 (캐싱용)
    @Column(nullable = false)
    private Long latestVersionNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // 보존 연한 필드 추가 -> 폐기관리용

    @Builder
    public Document(String title, User owner, Long latestVersionNumber) {
        this.title = title;
        this.owner = owner;
        this.latestVersionNumber = latestVersionNumber;
    }
}
