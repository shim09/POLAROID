package com.project.polaroid.repository;

import com.project.polaroid.entity.GoodsEntity;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.entity.PayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayRepository extends JpaRepository<PayEntity, Long> {

    PayEntity findTop1ByGoodsEntityAndMemberEntityOrderByIdDesc(GoodsEntity goodsEntity, MemberEntity memberEntity);

    List<PayEntity> findAllByMemberEntity(MemberEntity memberEntity);

    List<PayEntity> findAllByMemberEntityOrderByIdDesc(MemberEntity memberEntity);
}
