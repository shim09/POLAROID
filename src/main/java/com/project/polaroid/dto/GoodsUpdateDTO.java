package com.project.polaroid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsUpdateDTO {

    private Long goodsId;
    private Long memberId;
    private String goodsTitle;
    private int goodsStock;
    private int goodsPrice;
    private String goodsContents;
    private String goodsInFor;


}
