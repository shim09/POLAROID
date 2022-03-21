package com.project.polaroid.dto;

import com.project.polaroid.entity.PayEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayDetailDTO {

    private Long payId;
    private Long memberId;
    private Long goodsId;

    private String memberNickname;
    private String goodsTitle;
    private int count;
    private int goodsPrice;
    private int totalPrice;

    private List<GoodsPhotoDetailDTO> goodsPhoto;

    public static PayDetailDTO toPayDetailDTO(PayEntity payEntity) {
        PayDetailDTO payDetailDTO = new PayDetailDTO();
        payDetailDTO.setPayId(payEntity.getId());
        payDetailDTO.setMemberId(payEntity.getMemberEntity().getId());
        payDetailDTO.setGoodsId(payEntity.getGoodsEntity().getId());
        payDetailDTO.setMemberNickname(payEntity.getMemberEntity().getMemberNickname());
        payDetailDTO.setGoodsTitle(payEntity.getGoodsEntity().getGoodsTitle());
        payDetailDTO.setCount(payEntity.getCount());
        payDetailDTO.setGoodsPrice(payEntity.getGoodsEntity().getGoodsPrice());
        payDetailDTO.setTotalPrice(payEntity.getGoodsEntity().getGoodsPrice()*payEntity.getCount());
        payDetailDTO.setGoodsPhoto(GoodsPhotoDetailDTO.toGoodsPhotoDetailDTOList(payEntity.getGoodsEntity().getGoodsPhotoEntity()));
        return payDetailDTO;
    }

    public static List<PayDetailDTO> toPayDetailDTOList(List<PayEntity> payEntityList) {
        List<PayDetailDTO> payDetailDTOList = new ArrayList<>();
        for (PayEntity p: payEntityList) {
            payDetailDTOList.add(toPayDetailDTO(p));
        }
        return payDetailDTOList;
    }
}
