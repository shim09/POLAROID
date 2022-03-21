package com.project.polaroid.service;

import com.project.polaroid.dto.CommentDetailDTO;
import com.project.polaroid.dto.CommentSaveDTO;

import java.util.List;

public interface CommentService {
    Long save(CommentSaveDTO commentSaveDTO);

    List<CommentDetailDTO> findAll(Long boardId);

    void deleteById(Long commentId);
}
