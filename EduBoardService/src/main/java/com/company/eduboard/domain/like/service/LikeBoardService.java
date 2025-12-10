package com.company.eduboard.domain.like.service;

import com.company.eduboard.domain.board.entity.Board;
import com.company.eduboard.domain.board.repository.BoardRepository;
import com.company.eduboard.domain.like.entity.LikeBoard;
import com.company.eduboard.domain.like.repository.LikeBoardRepository;
import com.company.eduboard.domain.user.entity.User;
import com.company.eduboard.global.error.exception.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeBoardService {
    private final LikeBoardRepository likeBoardRepository;
    private final BoardRepository boardRepository;

    // 좋아요 토글 기능
    @Transactional
    public boolean toggleLike(Integer boardId, User user) {
        log.info("좋아요 요청 - boardId: {}, userId: {}", boardId, user.getUserId());
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(boardId));

        // 좋아요 상태 확인
//        LikeBoard existingLike = likeBoardRepository.findByBoard_BoardIdAndUser_UserId(boardId, userId);
        LikeBoard existingLike = likeBoardRepository.findByBoardAndUser(board, user)
                .orElseGet(() -> {
                        log.info("처음 좋아요 누름 - boardId: {}, userId: {}", boardId, user.getUserId());
                        return likeBoardRepository.save(LikeBoard.builder().board(board).user(user).build());
        });
        if (existingLike.getLikeBoardId() != null) {
            existingLike.toggleLike();
            log.info("좋아요 토글 - 현재 상태: {}", existingLike.isLiked());
        }

        return existingLike.isLiked();
    }

    @Transactional(readOnly = true)
    public long countLikes(Integer boardId) {
        log.info("게시글 좋아요 개수 조회 - boardId: {}", boardId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(boardId));
        return likeBoardRepository.countByBoardAndLikedIsTrue(board);
    }

    @Transactional(readOnly = true)
    public boolean isLikedByUser(Integer boardId, Integer loginUserId) {
        log.info("사용자 좋아요 여부 조회 - boardId: {}, userId: {}", boardId, loginUserId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(boardId));

        return likeBoardRepository.existsByBoardAndUser_UserIdAndLikedIsTrue(board, loginUserId);
    }
}
