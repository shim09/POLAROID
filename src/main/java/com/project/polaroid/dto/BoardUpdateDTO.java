package com.project.polaroid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateDTO {

    private Long boardId;
    private Long memberId;
    private String boardContents;

}
