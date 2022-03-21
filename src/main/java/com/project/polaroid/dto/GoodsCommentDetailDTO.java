package com.project.polaroid.dto;

import com.project.polaroid.entity.GoodsCommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCommentDetailDTO {
    private Long goodsCommentId;
    private Long goodsId;
    private Long memberId;
    private String memberNickName;
    private String memberEmail;
    private String goodsCommentContents;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static GoodsCommentDetailDTO toGoodsCommentDetailDTO(GoodsCommentEntity goodsCommentEntity) {
        GoodsCommentDetailDTO goodsCommentDetailDTO = new GoodsCommentDetailDTO();
        goodsCommentDetailDTO.setGoodsCommentId(goodsCommentEntity.getId());
        goodsCommentDetailDTO.setGoodsId(goodsCommentEntity.getId());
        goodsCommentDetailDTO.setMemberId(goodsCommentEntity.getMemberId().getId());
        goodsCommentDetailDTO.setMemberNickName(goodsCommentEntity.getMemberId().getMemberNickname());
        goodsCommentDetailDTO.setMemberEmail(goodsCommentEntity.getMemberId().getMemberEmail());
        goodsCommentDetailDTO.setGoodsCommentContents(goodsCommentEntity.getGoodsCommentContents());
        goodsCommentDetailDTO.setCreateTime(goodsCommentEntity.getCreateTime());
        goodsCommentDetailDTO.setUpdateTime(goodsCommentEntity.getUpdateTime());
        return goodsCommentDetailDTO;
    }

    public static List<GoodsCommentDetailDTO> toGoodsCommentDetailDTOList(List<GoodsCommentEntity> goodsCommentEntityList) {
        List<GoodsCommentDetailDTO> goodsCommentDetailDTOList = new ArrayList<>();
        for (GoodsCommentEntity c: goodsCommentEntityList) {
            goodsCommentDetailDTOList.add(GoodsCommentDetailDTO.toGoodsCommentDetailDTO(c));
        }
        return goodsCommentDetailDTOList;
    }
}
