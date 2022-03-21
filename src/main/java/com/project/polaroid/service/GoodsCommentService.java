package com.project.polaroid.service;

import com.project.polaroid.dto.GoodsCommentDetailDTO;
import com.project.polaroid.dto.GoodsCommentSaveDTO;

import java.util.List;

public interface GoodsCommentService {
    // 댓글 저장
    Long save(GoodsCommentSaveDTO goodsCommentSaveDTO);

    // 댓글 목록
    List<GoodsCommentDetailDTO> findAll(Long goodsId);

    // 댓글 삭제
    void deleteById(Long goodsCommentId);
}