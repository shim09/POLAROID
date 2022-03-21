package com.project.polaroid.service;

import com.project.polaroid.dto.ChatRoomFormDTO;
import com.project.polaroid.entity.ChatRoomEntity;
import com.project.polaroid.entity.ChatRoomJoinEntity;
import com.project.polaroid.entity.MemberEntity;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {

    Optional<ChatRoomEntity> findById(Long id);

    List<ChatRoomFormDTO> setting(List<ChatRoomJoinEntity> chatRoomJoins, MemberEntity user);


}
