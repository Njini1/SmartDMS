package com.company.eduboard.domain.document.controller;

import com.company.eduboard.domain.document.dto.request.DocumentRegisterRequest;
import com.company.eduboard.domain.document.dto.request.DocumentVersionRegisterRequest;
import com.company.eduboard.domain.document.dto.response.DocumentResponse;
import com.company.eduboard.domain.document.service.DocumentService;
import com.company.eduboard.domain.document.service.DocumentVersionService;
import com.company.eduboard.domain.user.service.CustomUserDetails;
import com.company.eduboard.global.error.exception.DocumentVersionConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
    private final DocumentService documentService;
    private final DocumentVersionService documentVersionService;

    // 문서 등록 화면 요청
    @GetMapping("/registerForm")
    public String getDocumentForm(Model model) {
        model.addAttribute("form", new DocumentRegisterRequest());
        return "document/form";
    }

    // 문서 등록
    @PostMapping("/register")
    public String registerDocument(@ModelAttribute("form") DocumentRegisterRequest registerRequest,
                                   @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long documentId = documentService.createDocument(registerRequest,
                userDetails.getUser());

        return "redirect:/document/" + documentId;
    }

    // 문서 수정 화면 요청(새 버전 문서 생성 화면 요청)
    @GetMapping("/{documentId}/updateForm")
    public String getDocumentUpdateForm(@PathVariable Long documentId,
                                        Model model) {
        DocumentResponse document = documentService.findDocumentById(documentId);

        model.addAttribute("document", document);

        return "document/updateForm";
    }

    @PostMapping("/{documentId}/update")
    public String registerNewVersionDocument(@PathVariable Long documentId,
                                             @ModelAttribute("form") DocumentVersionRegisterRequest registerRequest,
                                             @AuthenticationPrincipal CustomUserDetails userDetails,
                                             Model model) {

        // TODO: 추후 권한 검사 추가

        try {
            Long newVersionId = documentVersionService.createNewVersion(registerRequest,
                    userDetails.getUser());
            return "redirect:/document/" + documentId;
        } catch (DocumentVersionConflictException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            model.addAttribute("document",
                    documentService.findDocumentById(documentId));
            return "document/updateForm"; // TODO: 추후 비교 화면으로 변경
        }
    }
}
