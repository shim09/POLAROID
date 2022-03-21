package com.project.polaroid.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="chatMessage_table")
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @Column(name = "message_message", nullable = false)
    private String message;

    @Column(name = "message_time", nullable = false)
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoomEntity chatRoom;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity writer;

    public ChatMessageEntity(String message, LocalDateTime time, ChatRoomEntity chatRoom, MemberEntity writer){
        this.message=message;
        this.time=time;
        this.chatRoom=chatRoom;
        this.writer=writer;
    }

}
