package com.company.eduboard.domain.document.repository;

import com.company.eduboard.domain.document.entity.Document;
import com.company.eduboard.domain.document.entity.DocumentStatus;
import com.company.eduboard.global.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Page<Document> findAllByStatusOrderByUpdatedDateDesc(DocumentStatus status, Pageable pageable);
}
