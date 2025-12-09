package com.company.eduboard.domain.document.repository;

import com.company.eduboard.domain.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
