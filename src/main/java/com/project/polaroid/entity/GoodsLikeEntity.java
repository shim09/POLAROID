package com.project.polaroid.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="goodsLike_table")
public class GoodsLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="goodsLike_id")
    private Long id;

    // 해당 굿즈
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="goods_id")
    private GoodsEntity goodsEntity;

    // 찜 누른 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static GoodsLikeEntity toGoodsLikeEntity(GoodsEntity goodsEntity, MemberEntity memberEntity) {
        GoodsLikeEntity goodsLikeEntity = new GoodsLikeEntity();
        goodsLikeEntity.setGoodsEntity(goodsEntity);
        goodsLikeEntity.setMemberEntity(memberEntity);
        return goodsLikeEntity;
    }

}
