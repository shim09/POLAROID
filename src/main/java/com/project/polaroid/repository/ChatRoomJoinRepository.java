package com.project.polaroid.repository;

import com.project.polaroid.entity.ChatRoomEntity;
import com.project.polaroid.entity.ChatRoomJoinEntity;
import com.project.polaroid.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomJoinRepository extends JpaRepository<ChatRoomJoinEntity,Long> {

    public List<ChatRoomJoinEntity> findByMember(MemberEntity member);

    public List<ChatRoomJoinEntity> findByChatRoom(ChatRoomEntity chatRoom);
}
