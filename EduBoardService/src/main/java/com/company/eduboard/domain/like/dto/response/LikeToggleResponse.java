package com.company.eduboard.domain.like.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeToggleResponse {
    private boolean success;
    private boolean liked;
    private long likeCount;
    private String message;

    @Builder
    public LikeToggleResponse(boolean success, boolean liked, long likeCount, String message) {
        this.success = success;
        this.liked = liked;
        this.likeCount = likeCount;
        this.message = message;
    }
}
