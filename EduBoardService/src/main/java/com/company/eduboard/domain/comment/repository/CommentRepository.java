package com.company.eduboard.domain.comment.repository;

import com.company.eduboard.domain.board.entity.Board;
import com.company.eduboard.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBoardOrderByCreatedDateAsc(Board board);

    @Query("""
        SELECT c FROM Comment c
        JOIN FETCH c.user u
        JOIN FETCH c.rootComment rc
        LEFT JOIN FETCH c.parentComment pc
        LEFT JOIN FETCH pc.user pcu
        WHERE c.board = :board
        ORDER BY rc.commentId ASC, c.createdDate ASC
    """)
    List<Comment> findFlatByBoardOrderByRootThenCreated(Board board);
}