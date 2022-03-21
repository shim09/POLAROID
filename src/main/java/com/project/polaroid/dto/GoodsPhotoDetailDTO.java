package com.project.polaroid.dto;

import com.project.polaroid.entity.GoodsPhotoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsPhotoDetailDTO {

    private Long goodsPhotoId;
    private Long goodsId;
    private String goodsFilename;

    public static GoodsPhotoDetailDTO toGoodsPhotoDetailDTO(GoodsPhotoEntity goodsPhotoEntity) {
        GoodsPhotoDetailDTO goodsPhotoDetailDTO = new GoodsPhotoDetailDTO();
        goodsPhotoDetailDTO.setGoodsPhotoId(goodsPhotoEntity.getId());
        goodsPhotoDetailDTO.setGoodsId(goodsPhotoEntity.getGoodsEntity().getId());
        goodsPhotoDetailDTO.setGoodsFilename(goodsPhotoEntity.getGoodsFilename());
        return goodsPhotoDetailDTO;
    }

    public static List<GoodsPhotoDetailDTO> toGoodsPhotoDetailDTOList(List<GoodsPhotoEntity> goodsPhotoEntityList) {
        List<GoodsPhotoDetailDTO> goodsPhotoDetailDTOList = new ArrayList<>();
        for (GoodsPhotoEntity gp: goodsPhotoEntityList) {
            goodsPhotoDetailDTOList.add(toGoodsPhotoDetailDTO(gp));
        }
        return goodsPhotoDetailDTOList;
    }

}
