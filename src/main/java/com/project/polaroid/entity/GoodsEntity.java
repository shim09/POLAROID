package com.project.polaroid.entity;

import com.project.polaroid.dto.GoodsSaveDTO;
import com.project.polaroid.dto.GoodsUpdateDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "goods_table")
public class GoodsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id;

    // 제목
    @Column
    private String goodsTitle;

    // 굿즈 내용
    @Column
    private String goodsContents;

    // 재고
    @Column
    private int goodsStock;

    // 가격
    @Column
    private int goodsPrice;

    // 조회수
    @Column
    private int goodsView;

    // 세부 내용
    @Column
    private String goodsInFor;

    // 사진
    @OneToMany(mappedBy = "goodsEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GoodsPhotoEntity> GoodsPhotoEntity = new ArrayList<>();

    // 댓글
    @OneToMany(mappedBy = "goodsId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GoodsCommentEntity> goodsCommentEntityList = new ArrayList<>();

    // 찜
    @OneToMany(mappedBy = "goodsEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GoodsLikeEntity> goodsLikeEntityList = new ArrayList<>();

    // 멤버엔티티 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity goodsWriter;

    public static GoodsEntity toGoodsEntitySave(GoodsSaveDTO goodsSaveDTO, MemberEntity memberEntity) {
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsWriter(memberEntity);
        goodsEntity.setGoodsContents(goodsSaveDTO.getGoodsContents());
        goodsEntity.setGoodsTitle(goodsSaveDTO.getGoodsTitle());
        goodsEntity.setGoodsPrice(goodsSaveDTO.getGoodsPrice());
        goodsEntity.setGoodsStock(goodsSaveDTO.getGoodsStock());
        goodsEntity.setGoodsInFor(goodsSaveDTO.getGoodsInFor());
        goodsEntity.setGoodsView(0);
        return goodsEntity;
    }

    public static GoodsEntity toUpdateGoodsEntity(GoodsUpdateDTO goodsUpdateDTO, GoodsEntity goodsEntity, MemberEntity memberEntity) {
        GoodsEntity goods = new GoodsEntity();
        goods.setId(goodsUpdateDTO.getGoodsId());
        goods.setGoodsWriter(memberEntity);
        goods.setGoodsTitle(goodsUpdateDTO.getGoodsTitle());
        goods.setGoodsPrice(goodsUpdateDTO.getGoodsPrice());
        goods.setGoodsStock(goodsUpdateDTO.getGoodsStock());
        goods.setGoodsContents(goodsUpdateDTO.getGoodsContents());
        goods.setGoodsView(goodsEntity.getGoodsView());
        goods.setGoodsInFor(goodsUpdateDTO.getGoodsInFor());
        goods.setGoodsCommentEntityList(goodsEntity.getGoodsCommentEntityList());
        goods.setGoodsPhotoEntity(goodsEntity.getGoodsPhotoEntity());
        goods.setGoodsLikeEntityList(goodsEntity.getGoodsLikeEntityList());
        return goods;
    }

}
