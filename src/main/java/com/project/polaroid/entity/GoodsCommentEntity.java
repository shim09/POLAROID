package com.project.polaroid.entity;

import com.project.polaroid.dto.GoodsCommentSaveDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="goodscomment_table")
public class GoodsCommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="goodsComment_id")
    private Long id;

    // 굿즈 댓글 내용
    @Column
    private String goodsCommentContents;

    // 해당 판매글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="goods_id")
    private GoodsEntity goodsId;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity memberId;

    public static GoodsCommentEntity toGoodsCommentEntity(GoodsCommentSaveDTO goodsCommentSaveDTO, GoodsEntity goodsEntity, MemberEntity memberEntity) {
        GoodsCommentEntity goodsCommentEntity = new GoodsCommentEntity();
        goodsCommentEntity.setGoodsCommentContents(goodsCommentSaveDTO.getGoodsCommentContents());
        goodsCommentEntity.setGoodsId(goodsEntity);
        goodsCommentEntity.setMemberId(memberEntity);
        return goodsCommentEntity;
    }

}

