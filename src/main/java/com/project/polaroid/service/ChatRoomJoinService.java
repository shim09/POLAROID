package com.project.polaroid.service;

import com.project.polaroid.entity.ChatRoomEntity;
import com.project.polaroid.entity.ChatRoomJoinEntity;
import com.project.polaroid.entity.MemberEntity;

import java.util.List;

public interface ChatRoomJoinService {

    List<ChatRoomJoinEntity> findByUser(MemberEntity user);

    Long check(String user1,String user2);

    Long newRoom(String user1, String user2);

    void createRoom(String user, ChatRoomEntity chatRoom);

    List<ChatRoomJoinEntity> findByChatRoom(ChatRoomEntity chatRoom);

    void delete(ChatRoomJoinEntity chatRoomJoin);

    String findAnotherUser(ChatRoomEntity chatRoom, String name);

}
