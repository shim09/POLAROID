package com.project.polaroid.controller;

import com.project.polaroid.dto.GoodsCommentDetailDTO;
import com.project.polaroid.dto.GoodsCommentSaveDTO;
import com.project.polaroid.service.GoodsCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/goodsComment/*")
public class GoodsCommentController {

    private final GoodsCommentService gcs;

    @PostMapping("save")
    public @ResponseBody
    List<GoodsCommentDetailDTO> save (@ModelAttribute GoodsCommentSaveDTO goodsCommentSaveDTO) {
        System.out.println("댓글이 넘어오나요?1");
        Long goodsCommentId = gcs.save(goodsCommentSaveDTO);
        List<GoodsCommentDetailDTO> goodsCommentList = gcs.findAll(goodsCommentSaveDTO.getGoodsId());
        return goodsCommentList;
    }

    @DeleteMapping("{goodsCommentId}")
    public ResponseEntity delete (@PathVariable Long goodsCommentId) {
        System.out.println("댓글이 넘어오나요?2");
        gcs.deleteById(goodsCommentId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
