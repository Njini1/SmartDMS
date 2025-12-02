package com.company.eduboard.domain.comment.dto.response;

import com.company.eduboard.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class CommentResponse {
    private final Integer commentId;
    private final String content;
    private final String author;
    private final String createdDate;
    private final Integer parentCommentId; // null이면 최상위 댓글인 root임 -> 여기서 부모닉네인 필드로 대체해서 사용 -> 없애기
    private final String parentNickname; // 화면에서 @표시 용
    private final String status;

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .content(comment.getStatus().name().equals("DELETED") ? null : comment.getContent())
                .author(comment.getUser().getNickname())
                .createdDate(comment.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getCommentId() : null)
                .parentNickname(comment.getParentComment() != null ? comment.getParentComment().getUser().getNickname() : null)
                .status(comment.getStatus().name())
                .build();
    }
}
