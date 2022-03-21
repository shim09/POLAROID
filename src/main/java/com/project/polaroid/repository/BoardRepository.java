package com.project.polaroid.repository;

import com.project.polaroid.dto.BoardPagingDTO;
import com.project.polaroid.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {

    Page<BoardEntity> findByBoardContentsContaining(String keyword, Pageable pageable);

    List<BoardEntity> findAllByOrderByIdDesc();

    @Query (value = "SELECT a FROM BoardEntity  a WHERE a.memberId.id=:memberId")
    List<BoardEntity> boardCount(Long memberId);

    @Query (value = "SELECT a FROM BoardEntity  a WHERE a.memberId.id=:memberId ORDER BY a.id DESC ")
    List<BoardEntity> mypage(Long memberId);
}
