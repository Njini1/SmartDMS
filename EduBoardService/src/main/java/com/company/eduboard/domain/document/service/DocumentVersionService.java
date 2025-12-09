package com.company.eduboard.domain.document.service;

import com.company.eduboard.domain.document.dto.request.DocumentUpdateRequest;
import com.company.eduboard.domain.document.entity.Document;
import com.company.eduboard.domain.document.entity.DocumentVersion;
import com.company.eduboard.domain.document.repository.DocumentRepository;
import com.company.eduboard.domain.document.repository.DocumentVersionRepository;
import com.company.eduboard.domain.user.entity.User;
import com.company.eduboard.global.error.exception.DocumentNotFoundException;
import com.company.eduboard.global.error.exception.DocumentVersionConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DocumentVersionService {

    private final DocumentRepository documentRepository;
    private final DocumentVersionRepository documentVersionRepository;

    @Transactional
    public Long createNewVersion(DocumentUpdateRequest documentUpdateRequest, User editor) {
        Document document = documentRepository.findById(documentUpdateRequest.getDocumentId())
                .orElseThrow(() -> new DocumentNotFoundException("문서가 존재하지 않습니다."));

        // 동시성 체크 -> 낙관적 락 검사(수동 검증)
        if(!document.getLockVersion().equals(documentUpdateRequest.getLockVersion())) {
            throw new DocumentVersionConflictException("다른 사용자가 이미 문서를 수정했습니다. 새로고침 후 다시 시도해주세요.");
        }

        // 다음 버전 번호 생성
        Long nextVersionNumber = document.updateNextVersionNumber();

        // 새 버전 생성
        DocumentVersion newVersion = DocumentVersion.of(
                document,
                nextVersionNumber,
                documentUpdateRequest.getNewContent(),
                editor,
                documentUpdateRequest.getChangeReason()
        );

         documentVersionRepository.save(newVersion);

         // 변경된 문서 정보 저장 -> 더티체킹으로 인해 트랜잭션이 커밋되는 시점에 문서 버전(lockVersion) 업데이트 됨
        document.updateCurrentVersionAndTitle(
                newVersion,
                documentUpdateRequest.getNewTitle()
        );

        return newVersion.getVersionId();
    }
}
