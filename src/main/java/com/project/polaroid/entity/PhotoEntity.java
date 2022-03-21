package com.project.polaroid.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "photo_table")
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    // 해당 게시글
    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity boardId;

    @Column
    private String boardFilename;

}
