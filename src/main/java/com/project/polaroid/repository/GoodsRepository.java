package com.project.polaroid.repository;

import com.project.polaroid.entity.GoodsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends JpaRepository<GoodsEntity, Long> {

    Page<GoodsEntity> findByGoodsTitleContaining(String search, Pageable pageable);

    @Query(value = "SELECT a FROM GoodsEntity  a WHERE a.goodsWriter.memberNickname like concat('%',:writer,'%')")
    public Page<GoodsEntity> searchWriter(String writer, Pageable pageable);

    @Query (value = "SELECT a FROM GoodsEntity  a WHERE a.goodsWriter.id=:memberId")
    Page<GoodsEntity> findByIdGoodsWriter(Long memberId, Pageable pageable);


    @Modifying
    @Query("update GoodsEntity p set p.goodsView = p.goodsView + 1 where p.id = :goodsId")
    void viewUp(Long goodsId);

    @Modifying
    @Query("update GoodsEntity p set p.goodsStock = p.goodsStock - :count where p.id = :goodsId")
    void stockDown(Long goodsId, int count);

    List<GoodsEntity> findAllByOrderByIdDesc();

}

