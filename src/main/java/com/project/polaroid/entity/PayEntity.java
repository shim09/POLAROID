package com.project.polaroid.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "pay_table")
public class PayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private GoodsEntity goodsEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity memberEntity;

    @Column
    private int count;

    public static PayEntity toPayEntity(MemberEntity memberEntity, GoodsEntity goodsEntity, int count) {
        PayEntity payEntity = new PayEntity();
        payEntity.setMemberEntity(memberEntity);
        payEntity.setGoodsEntity(goodsEntity);
        payEntity.setCount(count);
        return payEntity;
    }
}
