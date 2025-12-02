package com.company.eduboard.domain.board.entity;

import com.company.eduboard.domain.user.entity.User;
import com.company.eduboard.global.entity.BaseTimeEntity;
import com.company.eduboard.global.enums.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status = Status.ACTIVE;

    @Builder
    public Board(User user, String title, String content, Status status) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public static Board of(User user, String title, String content) {
        return Board.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
    }

    public void updateTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void delete() {
        this.status = Status.DELETED;
    }
}
