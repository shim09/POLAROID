package com.project.polaroid.entity;

import com.project.polaroid.dto.CommentSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    // 댓글 내용
    @Column
    private String commentContents;

    // 해당 게시글
    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity boardId;

    // 작성용
    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberId;

    public static CommentEntity toCommentEntity(CommentSaveDTO commentSaveDTO, BoardEntity boardEntity, MemberEntity memberEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setBoardId(boardEntity);
        commentEntity.setMemberId(memberEntity);
        commentEntity.setCommentContents(commentSaveDTO.getCommentContents());
        return commentEntity;
    }
}

