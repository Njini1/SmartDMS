package com.company.eduboard.domain.comment.service;

import com.company.eduboard.domain.board.entity.Board;
import com.company.eduboard.domain.board.repository.BoardRepository;
import com.company.eduboard.domain.comment.dto.request.CommentRequest;
import com.company.eduboard.domain.comment.dto.response.CommentResponse;
import com.company.eduboard.domain.comment.entity.Comment;
import com.company.eduboard.domain.comment.repository.CommentRepository;
import com.company.eduboard.domain.user.entity.User;
import com.company.eduboard.domain.user.repository.UserRepository;
import com.company.eduboard.global.enums.Status;
import com.company.eduboard.global.error.exception.BoardNotFoundException;
import com.company.eduboard.global.error.exception.CommentNotFoundException;
import com.company.eduboard.global.error.exception.UnauthorizedActionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveComment(Integer boardId, User user, CommentRequest commentRequest) {
        log.info("댓글 등록 service - boardId: {}, userId: {}", boardId, user);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        Comment parentComment = null;
        Comment rootComment = null;
        if (commentRequest.getParentCommentId() != null) { // 대댓글인 경우
            parentComment = commentRepository.findById(commentRequest.getParentCommentId())
                    .orElseThrow(CommentNotFoundException::new);
            if (parentComment.getStatus() == Status.DELETED) {
                throw new IllegalArgumentException("삭제된 댓글에는 대댓글을 작성할 수 없습니다.");
            }
            rootComment = parentComment.getRootComment();
        }

        Comment comment = Comment.of(board, user, parentComment, rootComment, commentRequest.getContent());
        commentRepository.save(comment);

        if (rootComment == null) {
            // 루트 댓글인 경우, 자기 자신을 루트 댓글로 설정
            comment.setRootComment(comment); // 영속 상태라 update 쿼리 한 번 발생
        }
    }

    @Transactional
    public void updateComment(Integer commentId, Integer userId, String newContent) {
        log.info("댓글 수정 service - commentId: {}, userId: {}", commentId, userId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new UnauthorizedActionException("댓글 작성자만 수정할 수 있습니다.");
        }

        comment.updateContent(newContent);
    }

    @Transactional
    public void deleteComment(Integer commentId, Integer userId){
        log.info("댓글 삭제 service - commentId: {}, userId: {}", commentId, userId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new UnauthorizedActionException("본인 댓글만 삭제할 수 있습니다.");
        }
        comment.delete();
    }

    // 댓글 목록 조회
    @Transactional(readOnly = true)
    public List<CommentResponse> findCommentsByBoardId(Integer boardId) {
        log.info("댓글 목록 조회 service - boardId: {}", boardId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
        return commentRepository.findFlatByBoardOrderByRootThenCreated(board)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }
}
