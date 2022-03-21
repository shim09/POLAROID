package com.project.polaroid.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notice_table")
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "notice_id")
    private Long id;

    @Column (name = "notice_member")
    private Long noticeMember;

    @Column (name = "notice_room")
    private Long noticeRoom;

    @Column (name = "notice_message")
    private String noticeMessage;

    @Column (name = "notice_follow")
    private String noticeFollow;

    public NoticeEntity(Long noticeMember, Long noticeRoom, String noticeMessage) {
        this.noticeMember = noticeMember;
        this.noticeRoom = noticeRoom;
        this.noticeMessage = noticeMessage;
    }
}
