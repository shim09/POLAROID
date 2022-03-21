package com.project.polaroid.dto;

import com.project.polaroid.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDetailDTO {

    private Long commentId;
    private Long boardId;
    private Long memberId;
    private String memberNickname;
    private String memberEmail;
    private String commentContents;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static CommentDetailDTO toCommentDetailDTO(CommentEntity commentEntity) {
        CommentDetailDTO commentDetailDTO = new CommentDetailDTO();
        commentDetailDTO.setCommentId(commentEntity.getId());
        commentDetailDTO.setMemberNickname(commentEntity.getMemberId().getMemberNickname());
        commentDetailDTO.setMemberEmail(commentEntity.getMemberId().getMemberEmail());
        commentDetailDTO.setCommentContents(commentEntity.getCommentContents());
        commentDetailDTO.setCreateTime(commentEntity.getCreateTime());
        commentDetailDTO.setUpdateTime(commentEntity.getUpdateTime());
        commentDetailDTO.setBoardId(commentEntity.getBoardId().getId());
        commentDetailDTO.setMemberId(commentEntity.getMemberId().getId());
        return commentDetailDTO;
    }

    public static List<CommentDetailDTO> toCommentDetailDTOList(List<CommentEntity> commentEntityList) {
        List<CommentDetailDTO> commentDetailDTOList = new ArrayList<>();
        for (CommentEntity c : commentEntityList) {
            commentDetailDTOList.add(CommentDetailDTO.toCommentDetailDTO(c));
        }
        return commentDetailDTOList;
    }

}

