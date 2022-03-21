package com.project.polaroid.controller;

import com.project.polaroid.dto.CommentDetailDTO;
import com.project.polaroid.dto.CommentSaveDTO;
import com.project.polaroid.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment/*")
public class CommentController {
    private final CommentService cs;

    @PostMapping("save")
    public @ResponseBody List<CommentDetailDTO> save(@ModelAttribute CommentSaveDTO commentSaveDTO) {
        Long commentId = cs.save(commentSaveDTO);
        List<CommentDetailDTO> commentList = cs.findAll(commentSaveDTO.getBoardId());
        return commentList;
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity deleteById(@PathVariable Long commentId) {
        cs.deleteById(commentId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
