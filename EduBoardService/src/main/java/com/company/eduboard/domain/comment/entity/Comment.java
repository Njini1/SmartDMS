package com.company.eduboard.domain.comment.entity;

import com.company.eduboard.domain.board.entity.Board;
import com.company.eduboard.global.enums.Status;
import com.company.eduboard.domain.user.entity.User;
import com.company.eduboard.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCommentId")
    private Comment parentComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rootCommentId")
    private Comment rootComment;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status = Status.ACTIVE;

    @Builder
    public Comment(Board board, User user, Comment parentComment, Comment rootComment, String content) {
        this.board = board;
        this.user = user;
        this.parentComment = parentComment;
        this.rootComment = rootComment;
        this.content = content;
    }

    public static Comment of(Board board, User user, Comment parentComment, Comment rootComment, String content) {
        return Comment.builder()
                .board(board)
                .user(user)
                .parentComment(parentComment)
                .rootComment(rootComment) // service 단에서 rootComment 설정
                .content(content)
                .build();
    }

    public void setRootComment(Comment rootComment) {
        this.rootComment = rootComment;
    }

    public boolean isRootComment() {
        return this.parentComment == this.rootComment;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void delete() {
        this.status = Status.DELETED;
    }
}