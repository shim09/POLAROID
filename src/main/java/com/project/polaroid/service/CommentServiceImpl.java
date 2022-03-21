package com.project.polaroid.service;


import com.project.polaroid.dto.CommentDetailDTO;
import com.project.polaroid.dto.CommentSaveDTO;
import com.project.polaroid.entity.BoardEntity;
import com.project.polaroid.entity.CommentEntity;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.repository.BoardRepository;
import com.project.polaroid.repository.CommentRepository;
import com.project.polaroid.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository cr;
    private final BoardRepository br;
    private final MemberRepository mr;

    @Override
    public Long save(CommentSaveDTO commentSaveDTO) {
        BoardEntity boardEntity = br.findById(commentSaveDTO.getBoardId()).get();
        MemberEntity memberEntity = mr.findById(commentSaveDTO.getMemberId()).get();
        CommentEntity commentEntity = CommentEntity.toCommentEntity(commentSaveDTO, boardEntity, memberEntity);
        Long commentId = cr.save(commentEntity).getId();
        return commentId;
    }

    @Override
    public List<CommentDetailDTO> findAll(Long boardId) {
        BoardEntity boardEntity = br.findById(boardId).get();
        List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList();
        List<CommentDetailDTO> commentList = CommentDetailDTO.toCommentDetailDTOList(commentEntityList);
        return commentList;
    }

    @Override
    public void deleteById(Long commentId) {
        cr.deleteById(commentId);
    }
}