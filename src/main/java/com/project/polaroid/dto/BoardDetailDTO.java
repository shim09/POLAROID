package com.project.polaroid.dto;

import com.project.polaroid.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailDTO {

    private Long boardId;
    private Long memberId;
    private String memberNickname;
    private String boardContents;
    private String memberEmail;
    private int boardLikeCount;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private List<PhotoDetailDTO> photo;
    private List<CommentDetailDTO> commentList;
    private String memberFilename;

    public static BoardDetailDTO toBoardDetailDTO(BoardEntity boardEntity) {
        BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
        boardDetailDTO.setBoardId(boardEntity.getId());
        boardDetailDTO.setMemberId(boardEntity.getMemberId().getId());
        boardDetailDTO.setMemberNickname(boardEntity.getMemberId().getMemberNickname());
        boardDetailDTO.setBoardContents(boardEntity.getBoardContents());
        boardDetailDTO.setBoardLikeCount(boardEntity.getLikeEntityList().size());
        boardDetailDTO.setCreateTime(boardEntity.getCreateTime());
        boardDetailDTO.setUpdateTime(boardEntity.getUpdateTime());
        boardDetailDTO.setPhoto(PhotoDetailDTO.toPhotoDetailDTOList(boardEntity.getPhotoEntity()));
        boardDetailDTO.setMemberFilename(boardEntity.getMemberId().getMemberFilename());
        boardDetailDTO.setCommentList(CommentDetailDTO.toCommentDetailDTOList(boardEntity.getCommentEntityList()));
        boardDetailDTO.setMemberEmail(boardEntity.getMemberId().getMemberEmail());
        return boardDetailDTO;
    }


    public static List<BoardDetailDTO> toBoardDetailDTOList(List<BoardEntity> boardEntityList) {
        List<BoardDetailDTO> boardDetailDTOList = new ArrayList<>();
        for (BoardEntity b: boardEntityList) {
            boardDetailDTOList.add(toBoardDetailDTO(b));
        }
        return boardDetailDTOList;
    }

}
