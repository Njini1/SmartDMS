package com.company.eduboard.domain.board.repository;

import com.company.eduboard.domain.board.entity.Board;
import com.company.eduboard.global.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findAllByStatus(Status status, Pageable pageable);
}
