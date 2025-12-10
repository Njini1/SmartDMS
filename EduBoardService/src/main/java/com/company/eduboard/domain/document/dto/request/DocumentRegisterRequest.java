package com.company.eduboard.domain.document.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentRegisterRequest {
    private String title;
    private String content;
}
