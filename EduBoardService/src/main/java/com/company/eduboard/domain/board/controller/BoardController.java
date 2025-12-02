package com.company.eduboard.domain.board.controller;

import com.company.eduboard.domain.board.dto.request.BoardRequest;
import com.company.eduboard.domain.board.dto.response.BoardResponse;
import com.company.eduboard.domain.board.service.BoardService;
import com.company.eduboard.domain.comment.dto.response.CommentResponse;
import com.company.eduboard.domain.comment.service.CommentService;
import com.company.eduboard.domain.user.entity.User;
import com.company.eduboard.domain.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    // 게시글 목록 확인
    @GetMapping("/list")
    public String getBoardList(@RequestParam(defaultValue = "0") int page,
                               Model model) {
        log.info("게시글 목록 페이지 요청");
        model.addAttribute("boards", boardService.findAllBoards(page));
        return "board/list";
    }

    // 상세 게시글 확인
    @GetMapping("/{boardId}")
    public String getDetailBoard(@PathVariable Integer boardId,
                                 Model model,
                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("게시글 상세 페이지 요청 - boardId: {}", boardId);
        User user = (userDetails != null) ? userDetails.getUser() : null;
        Integer userId = (user != null) ? user.getUserId() : null;
        BoardResponse board = boardService.findBoardDetailById(boardId, userId);
        List<CommentResponse> comments = commentService.findCommentsByBoardId(boardId);

        model.addAttribute("board", board);
        model.addAttribute("comments", comments);
        model.addAttribute("loginUser", user);

        return "board/detail";
    }

    // 게시글 작성 화면 요청
    @GetMapping("/registerForm")
    public String getBoardForm(Model model) {
        log.info("게시글 작성 페이지 요청");
        model.addAttribute("boardRequest", new BoardRequest());
        return "board/form";
    }

    // 게시글 등록
    @PostMapping("/register")
    public String registerBoard(@ModelAttribute BoardRequest boardRequest,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("게시글 등록 요청 - 작성자: {}, 제목: {}",
                userDetails.getUser().getEmail(), boardRequest.getTitle());
        boardService.saveBoard(boardRequest, userDetails.getUser());
        return "redirect:/board/list";
    }

    // 게시글 수정 화면 요청
    @GetMapping("/{boardId}/updateForm")
    public String getUpdateForm(@PathVariable Integer boardId, Model model) {
        log.info("게시글 수정 페이지 요청 - boardId: {}", boardId);
        model.addAttribute("board", boardService.findBoardForEdit(boardId));
        return "board/updateForm";
    }

    // 게시글 수정
    @PostMapping("/{boardId}/update")
    public String updateBoard(@PathVariable Integer boardId,
                              @ModelAttribute BoardRequest boardRequest,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("게시글 수정 요청 - boardId: {}, 요청자: {}", boardId, userDetails.getUser().getEmail());
        boardService.updateBoard(boardId, boardRequest, userDetails.getUser().getUserId());
        return "redirect:/board/" + boardId;
    }

    // 게시글 삭제
    @PostMapping("/{boardId}/delete")
    public String delete(@PathVariable Integer boardId,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("게시글 삭제 요청 - boardId: {}, 요청자: {}", boardId, userDetails.getUser().getEmail());
        boardService.deleteBoard(boardId, userDetails.getUser().getUserId());
        return "redirect:/board/list";
    }
}
