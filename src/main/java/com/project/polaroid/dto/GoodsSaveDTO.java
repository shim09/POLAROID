package com.project.polaroid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GoodsSaveDTO {

    private Long memberId;
    private String goodsTitle;
    private String goodsContents;
    private int goodsPrice;
    private int goodsStock;
    private List<MultipartFile> goodsFile;
    private String goodsInFor;



}