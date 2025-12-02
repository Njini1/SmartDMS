package com.company.eduboard.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass  // 공통 매핑 정보 상속
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 활성화
public abstract class BaseTimeEntity {

    // 생성일시 자동 저장
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    // 수정일시 자동 저장
    @LastModifiedDate
    private LocalDateTime updatedDate;

}
