package com.company.eduboard.domain.document.repository;

import com.company.eduboard.domain.document.entity.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Long> {
}
