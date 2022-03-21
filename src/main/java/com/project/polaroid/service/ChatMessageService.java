package com.project.polaroid.service;

import com.project.polaroid.dto.ChatMessageFormDTO;

public interface ChatMessageService {

    void save(ChatMessageFormDTO message);

    int count(Long memberId);

    void deleteCount(Long chatRoomId, Long id);
}
