package com.project.polaroid.repository;

import com.project.polaroid.entity.BoardEntity;
import com.project.polaroid.entity.LikeEntity;
import com.project.polaroid.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    LikeEntity findByBoardIdAndMemberId(BoardEntity boardId, MemberEntity memberId);

    void deleteByBoardIdAndMemberId(BoardEntity boardId, MemberEntity memberId);

    // hsw 3.13추가 짐 목록
    @Query(value = "SELECT a FROM LikeEntity a WHERE a.memberId.id= :id ORDER BY a.id DESC ")
    List<LikeEntity> likeList(Long id);
}