package com.project.polaroid.dto;

import lombok.Data;

@Data
public class GoodsLikeDTO {
    // pk값으로 쓸 번호
    private long goodsLikeId;
    // 게시글 번호
    private long goodsId;
    // 회원 번호
    private long memberId;
    // 좋아요
    private int goodsLikeStatus;

}