package com.company.eduboard.domain.like.entity;

import com.company.eduboard.domain.board.entity.Board;
import com.company.eduboard.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "like_boards", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"board_id", "user_id"})
})
public class LikeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likeBoardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean liked = false;

    @Builder
    public LikeBoard(Board board, User user) {
        this.board = board;
        this.user = user;
    }

    // 좋아요 상태 토글 메서드
    public void toggleLike() {
        this.liked = !this.liked;
    }
}
