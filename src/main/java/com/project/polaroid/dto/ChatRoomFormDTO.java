package com.project.polaroid.dto;

import com.project.polaroid.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomFormDTO {

    private Long id;
    private String writer;
    private String lastMessage;
    private LocalDateTime time;
    private MemberEntity member;
    private int messageNotice;

    public void makeChatRoomForm(String message, String anotherUser, LocalDateTime time) {
        this.lastMessage = message;
        this.writer = anotherUser;
        this.time = time;
    }

}
