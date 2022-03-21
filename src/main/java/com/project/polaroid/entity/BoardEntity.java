package com.project.polaroid.entity;

import com.project.polaroid.dto.BoardSaveDTO;
import com.project.polaroid.dto.BoardUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    // 내용
    @Column(length = 2000)
    private String boardContents;

    // 사진
    @OneToMany(mappedBy = "boardId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PhotoEntity> photoEntity = new ArrayList<>();

    // 댓글
    @OneToMany(mappedBy = "boardId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    // 좋아요
    @OneToMany(mappedBy = "boardId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LikeEntity> likeEntityList = new ArrayList<>();

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberId;

    public static BoardEntity toBoardEntity(BoardSaveDTO boardSaveDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setMemberId(memberEntity);
        boardEntity.setBoardContents(boardSaveDTO.getBoardContents());
        return boardEntity;
    }

    public static BoardEntity toUpdateBoardEntity(BoardUpdateDTO boardUpdateDTO, BoardEntity boardEntity, MemberEntity memberEntity) {
        BoardEntity updateBoardEntity = new BoardEntity();
        updateBoardEntity.setId(boardEntity.getId());
        updateBoardEntity.setMemberId(memberEntity);
        updateBoardEntity.setPhotoEntity(boardEntity.getPhotoEntity());
        updateBoardEntity.setLikeEntityList(boardEntity.getLikeEntityList());
        updateBoardEntity.setCommentEntityList(boardEntity.getCommentEntityList());
        updateBoardEntity.setBoardContents(boardUpdateDTO.getBoardContents());
        return updateBoardEntity;
    }
}
