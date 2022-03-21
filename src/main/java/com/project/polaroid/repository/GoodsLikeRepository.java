package com.project.polaroid.repository;

import com.project.polaroid.entity.GoodsEntity;
import com.project.polaroid.entity.GoodsLikeEntity;
import com.project.polaroid.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsLikeRepository extends JpaRepository<GoodsLikeEntity, Long> {

    GoodsLikeEntity findByGoodsEntityAndMemberEntity(GoodsEntity goodsEntity, MemberEntity memberEntity);

    void deleteByGoodsEntityAndMemberEntity(GoodsEntity goodsEntity, MemberEntity memberEntity);

    List<GoodsLikeEntity> findAllByMemberEntity(MemberEntity memberEntity);

    // hsw 3.13추가 짐 목록
    @Query(value = "SELECT a FROM GoodsLikeEntity a WHERE a.memberEntity.id= :id ORDER BY a.id DESC ")
    List<GoodsLikeEntity> pickList(Long id);
}

