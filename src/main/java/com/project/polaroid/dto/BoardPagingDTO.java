package com.project.polaroid.dto;

import com.project.polaroid.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardPagingDTO {

    private Long boardId;
    private String memberNickname;
    private String boardContents;
    private List<PhotoDetailDTO> photoList;


}
