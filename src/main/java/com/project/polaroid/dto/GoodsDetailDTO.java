package com.project.polaroid.dto;

import com.project.polaroid.entity.GoodsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailDTO {
    private Long GoodsId;
    private Long memberId;
    private String goodsWriter;
    private String goodsTitle;
    private String goodsContents;
    private int goodsPrice;
    private int goodsStock;
    private String goodsFilename;
    private LocalDateTime goodsDate;
    private int goodsView;
    private int goodsLikeCount;
    private String goodsInFor;

    private List<GoodsCommentDetailDTO> goodsCommentList;

    private String memberFilename;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private List<GoodsPhotoDetailDTO> goodsPhoto;

    public static GoodsDetailDTO toGoodsDetailDTO(GoodsEntity goodsEntity) {
        GoodsDetailDTO goodsDetailDTO = new GoodsDetailDTO();
        goodsDetailDTO.setGoodsId(goodsEntity.getId());
        goodsDetailDTO.setGoodsWriter(goodsEntity.getGoodsWriter().getMemberNickname());
        goodsDetailDTO.setGoodsTitle(goodsEntity.getGoodsTitle());
        goodsDetailDTO.setGoodsContents(goodsEntity.getGoodsContents());
        goodsDetailDTO.setGoodsPrice(goodsEntity.getGoodsPrice());
        goodsDetailDTO.setGoodsView(goodsEntity.getGoodsView());
        goodsDetailDTO.setGoodsLikeCount(goodsEntity.getGoodsLikeEntityList().size());
        goodsDetailDTO.setCreateTime(goodsEntity.getCreateTime());
        goodsDetailDTO.setUpdateTime(goodsEntity.getUpdateTime());
        goodsDetailDTO.setGoodsInFor(goodsEntity.getGoodsInFor());
        goodsDetailDTO.setGoodsStock(goodsEntity.getGoodsStock());
        goodsDetailDTO.setGoodsCommentList(GoodsCommentDetailDTO.toGoodsCommentDetailDTOList(goodsEntity.getGoodsCommentEntityList()));

        goodsDetailDTO.setMemberId(goodsEntity.getGoodsWriter().getId());

        goodsDetailDTO.setMemberFilename(goodsEntity.getGoodsWriter().getMemberFilename());

        goodsDetailDTO.setGoodsPhoto(GoodsPhotoDetailDTO.toGoodsPhotoDetailDTOList(goodsEntity.getGoodsPhotoEntity()));

        return goodsDetailDTO;
    }

    public static List<GoodsDetailDTO> toChangeDTOList(List<GoodsEntity> goodsEntityList) {
        List<GoodsDetailDTO> goodsDetailDTOList = new ArrayList<>();
        for (GoodsEntity m: goodsEntityList) {
            goodsDetailDTOList.add(toGoodsDetailDTO(m));
        }
        return goodsDetailDTOList;
    }

}

