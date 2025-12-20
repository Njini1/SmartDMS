package com.company.eduboard.domain.document.service;

import com.company.eduboard.domain.document.dto.request.DocumentRegisterRequest;
import com.company.eduboard.domain.document.dto.response.DocumentListItemResponse;
import com.company.eduboard.domain.document.dto.response.DocumentResponse;
import com.company.eduboard.domain.document.entity.Document;
import com.company.eduboard.domain.document.entity.DocumentStatus;
import com.company.eduboard.domain.document.entity.DocumentVersion;
import com.company.eduboard.domain.document.repository.DocumentRepository;
import com.company.eduboard.domain.document.repository.DocumentVersionRepository;
import com.company.eduboard.domain.user.entity.User;
import com.company.eduboard.global.error.exception.DocumentNotFoundException;
import com.company.eduboard.global.util.HtmlContentProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentVersionRepository documentVersionRepository;
    private static final int PAGE_SIZE = 10;

    // 최초 문서 생성
    @Transactional
    public Long createDocument(DocumentRegisterRequest documentRegisterRequest, User user) {
        Document document = Document.of(
                documentRegisterRequest.getTitle(),
                user
        );

        documentRepository.save(document);

        var contentProcessor = HtmlContentProcessor.process(documentRegisterRequest.getContent());

        // 초기 버전 생성
        DocumentVersion initialVersion = DocumentVersion.of(
                document,
                1L,
                contentProcessor.content(),
                contentProcessor.contentText(),
                contentProcessor.contentSignature(),
                user,
                null
        );

        documentVersionRepository.save(initialVersion);

        document.updateCurrentVersionAndTitle(
                initialVersion,
                documentRegisterRequest.getTitle());

        return document.getDocumentId();
    }

    @Transactional(readOnly = true)
    public DocumentResponse findDocumentById(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException(documentId));

        return DocumentResponse.from(
                document.getDocumentId(),
                document.getTitle(),
                document.getCurrentVersion().getContent(),
                document.getLockVersion());
    }

    @Transactional(readOnly = true)
    public Page<DocumentListItemResponse> findAllDocuments(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return documentRepository.findAllByStatusOrderByUpdatedDateDesc(DocumentStatus.ACTIVE, pageable)
                .map(document -> DocumentListItemResponse.from(
                        document.getDocumentId(),
                        document.getTitle(),
                        document.getOwner().getNickname(),
                        document.getCurrentVersion().getVersionNumber(),
                        document.getLockVersion(),
                        document.getUpdatedDate()
                ));
    }
}
