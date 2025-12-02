package com.company.eduboard.domain.comment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private Integer parentCommentId;
    private String content;
}
