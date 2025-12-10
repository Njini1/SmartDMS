package com.company.eduboard.domain.comment.controller;

import com.company.eduboard.domain.comment.dto.request.CommentRequest;
import com.company.eduboard.domain.comment.service.CommentService;
import com.company.eduboard.domain.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{boardId}")
    public String addComment(@PathVariable Integer boardId,
                             @ModelAttribute CommentRequest commentRequest,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("댓글 작성 요청 - boardId: {}, userId: {}", boardId, userDetails.getUser().getUserId());
        commentService.saveComment(boardId, userDetails.getUser(), commentRequest);
        return "redirect:/board/" + boardId + "#comments";
    }

    // 댓글 수정
    @PostMapping("/{commentId}/update")
    public String updateComment(@PathVariable Integer commentId,
                                @RequestParam Integer boardId, // TODO: dto로 변경
                                @RequestParam String newContent,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("댓글 수정 요청 - commentId: {}, userId: {}", commentId, userDetails.getUser().getUserId());
        commentService.updateComment(commentId, userDetails.getUser().getUserId(), newContent);
        return "redirect:/board/" + boardId + "#comments";
    }

    // 댓글 삭제
    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable Integer commentId,
                                @RequestParam Integer boardId, // TODO: dto로 변경
                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("댓글 삭제 요청 - commentId: {}, userId: {}", commentId, userDetails.getUser().getUserId());
        commentService.deleteComment(commentId, userDetails.getUser().getUserId());
        return "redirect:/board/" + boardId + "#comments";
    }
}
