package com.company.eduboard.domain.board.dto.response;

import com.company.eduboard.domain.board.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class BoardResponse {
    private final Integer boardId;
    private final String title;
    private final String content;
    private final String author;
    private final String status;
    private final String createdDate;
    private final String updatedDate;
    private final long likeCount;
    private final boolean isLiked;

    public static BoardResponse from(Board board){
        return BoardResponse.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getUser().getNickname())
                .status(board.getStatus().name())
                .createdDate(board.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updatedDate(board.getUpdatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }

    public static BoardResponse from(Board board, long likeCount){
        return from(board).toBuilder()
                .likeCount(likeCount)
                .build();
    }

    public static BoardResponse from(Board board, long likeCount, boolean isLiked){
        return from(board).toBuilder()
                .likeCount(likeCount)
                .isLiked(isLiked)
                .build();
    }

    private BoardResponseBuilder toBuilder() {
        return BoardResponse.builder()
                .boardId(this.boardId)
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .status(this.status)
                .createdDate(this.createdDate)
                .updatedDate(this.updatedDate)
                .likeCount(this.likeCount)
                .isLiked(this.isLiked);
    }
}
