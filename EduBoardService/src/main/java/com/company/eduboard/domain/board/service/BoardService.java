package com.company.eduboard.domain.board.service;

import com.company.eduboard.domain.board.dto.request.BoardRequest;
import com.company.eduboard.domain.board.dto.response.BoardResponse;
import com.company.eduboard.domain.board.entity.Board;
import com.company.eduboard.domain.board.repository.BoardRepository;
import com.company.eduboard.domain.like.service.LikeBoardService;
import com.company.eduboard.domain.user.entity.User;
import com.company.eduboard.global.enums.Status;
import com.company.eduboard.global.error.exception.BoardNotFoundException;
import com.company.eduboard.global.error.exception.UnauthorizedActionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final LikeBoardService likeService;
    private static final int PAGE_SIZE = 10;

    @Transactional(readOnly = true)
    public Page<BoardResponse> findAllBoards(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        log.info("모든 게시글 조회 요청");
        // TODO: 없는 페이징 조회시 0번째 페이지 반환

        return boardRepository.findAllByStatus(Status.ACTIVE, pageable)
                .map(board -> BoardResponse.from(board, likeService.countLikes(board.getBoardId())));
    }

    @Transactional(readOnly = true)
    public BoardResponse findBoardDetailById(Integer boardId, Integer loginUserId) {
        log.info("게시글 상세 조회 요청 - boardId: {}", boardId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글이 존재하지 않습니다."));

        long likeCount = likeService.countLikes(boardId);
        boolean isLiked = loginUserId != null && likeService.isLikedByUser(boardId, loginUserId);

        return BoardResponse.from(board, likeCount, isLiked);
    }

    @Transactional(readOnly = true)
    public BoardResponse findBoardForEdit(Integer boardId) {
        log.info("게시글 수정용 조회 - boardId: {}", boardId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글이 존재하지 않습니다."));
        return BoardResponse.from(board);
    }

    @Transactional
    public void saveBoard(BoardRequest boardRequest, User user) {
        log.info("게시글 등록 시도 - 작성자: {}, 제목: {}", user.getEmail(), boardRequest.getTitle());
//        boardRepository.save(Board.of(user, boardRequest.getTitle(), boardRequest.getContent()));
        Board board = Board.of(user, boardRequest.getTitle(), boardRequest.getContent());
        Board saved = boardRepository.save(board);
        log.info("게시글 등록 완료 - boardId: {}", saved.getBoardId());
    }

    @Transactional
    public void updateBoard(Integer boardId, BoardRequest boardRequest, Integer userId) {
        log.info("게시글 수정 시도 - boardId: {}, 요청자 userId: {}", boardId, userId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글이 존재하지 않습니다."));

        if (!board.getUser().getUserId().equals(userId)) {
            log.warn("권한 없음 - 작성자 ID: {}, 요청자 ID: {}", board.getUser().getUserId(), userId);
            throw new UnauthorizedActionException("작성자만 게시글을 수정할 수 있습니다.");
        }

        board.updateTitleAndContent(boardRequest.getTitle(), boardRequest.getContent());
    }

    @Transactional
    public void deleteBoard(Integer boardId, Integer userId) {
        log.info("게시글 삭제 시도 - boardId: {}, 요청자 userId: {}", boardId, userId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글이 존재하지 않습니다."));

        if (!board.getUser().getUserId().equals(userId)) {
            log.warn("권한 없음 - 작성자 ID: {}, 요청자 ID: {}", board.getUser().getUserId(), userId);
            throw new UnauthorizedActionException("작성자만 게시글을 삭제할 수 있습니다.");
        }

        board.delete();
    }
}
