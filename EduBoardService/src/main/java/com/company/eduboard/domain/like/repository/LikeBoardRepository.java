package com.company.eduboard.domain.like.repository;

import com.company.eduboard.domain.board.entity.Board;
import com.company.eduboard.domain.like.entity.LikeBoard;
import com.company.eduboard.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Integer> {
    Optional<LikeBoard> findByBoardAndUser(Board board, User user);
    long countByBoardAndLikedIsTrue(Board board);
    boolean existsByBoardAndUser_UserIdAndLikedIsTrue(Board board, Integer userId);

}
