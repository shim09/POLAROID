package com.project.polaroid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageFormDTO {
    private Long ChatRoomId;
    private String receiver;
    private String sender;
    private String message;
}
