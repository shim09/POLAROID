package com.project.polaroid.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="chatRoomJoin_table")
public class ChatRoomJoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name =  "member_id", nullable = false)
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoomEntity chatRoom;

    public ChatRoomJoinEntity(MemberEntity member , ChatRoomEntity chatRoom){
        this.member=member;
        this.chatRoom=chatRoom;
    }

}
