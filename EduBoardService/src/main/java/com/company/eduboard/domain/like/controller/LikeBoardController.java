package com.company.eduboard.domain.like.controller;

import com.company.eduboard.domain.like.dto.response.LikeToggleResponse;
import com.company.eduboard.domain.like.service.LikeBoardService;
import com.company.eduboard.domain.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeBoardController {
    private final LikeBoardService likeBoardService;

    @PostMapping("/{boardId}/toggle")
    @ResponseBody
    public LikeToggleResponse toggleLike(@PathVariable Integer boardId,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return LikeToggleResponse.builder()
                    .success(false)
                    .message("로그인 후 이용 가능합니다.")
                    .build();
        }

        log.info("좋아요 토글 요청 - boardId: {}, userId: {}", boardId, userDetails.getUser().getUserId());

        boolean liked = likeBoardService.toggleLike(boardId, userDetails.getUser());
        long likeCount = likeBoardService.countLikes(boardId);

        return LikeToggleResponse.builder()
                .success(true)
                .liked(liked)
                .likeCount(likeCount)
                .build();
    }
}
