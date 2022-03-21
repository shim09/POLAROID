package com.project.polaroid.service;

import com.project.polaroid.dto.GoodsCommentDetailDTO;
import com.project.polaroid.dto.GoodsCommentSaveDTO;
import com.project.polaroid.entity.GoodsCommentEntity;
import com.project.polaroid.entity.GoodsEntity;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.repository.GoodsCommentRepository;
import com.project.polaroid.repository.GoodsRepository;
import com.project.polaroid.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsCommentServiceImpl implements GoodsCommentService{
    private final GoodsCommentRepository gcr;
    private final GoodsRepository gr;
    private final MemberRepository mr;

    // 댓글 저장
    @Override
    public Long save(GoodsCommentSaveDTO goodsCommentSaveDTO) {
        GoodsEntity goodsEntity = gr.findById(goodsCommentSaveDTO.getGoodsId()).get();
        MemberEntity memberEntity = mr.findById(goodsCommentSaveDTO.getMemberId()).get();
        GoodsCommentEntity goodsCommentEntity = GoodsCommentEntity.toGoodsCommentEntity(goodsCommentSaveDTO, goodsEntity, memberEntity);
        Long goodsCommentId = gcr.save(goodsCommentEntity).getId();
        return goodsCommentId;
    }

    // 댓글 출력
    @Override
    public List<GoodsCommentDetailDTO> findAll(Long goodsId) {
        GoodsEntity goodsEntity = gr.findById(goodsId).get();
        List<GoodsCommentEntity> goodsCommentEntityList = goodsEntity.getGoodsCommentEntityList();
        List<GoodsCommentDetailDTO> goodsCommentList = GoodsCommentDetailDTO.toGoodsCommentDetailDTOList(goodsCommentEntityList);
        return goodsCommentList;
    }

    // 댓글 삭제
    @Override
    public void deleteById(Long goodsCommentId) {
        gcr.deleteById(goodsCommentId);

    }
}
