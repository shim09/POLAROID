package com.project.polaroid.service;

import com.project.polaroid.entity.ChatRoomEntity;
import com.project.polaroid.entity.ChatRoomJoinEntity;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.repository.ChatRoomJoinRepository;
import com.project.polaroid.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatRoomJoinServiceImpl implements ChatRoomJoinService{

    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public List<ChatRoomJoinEntity> findByUser(MemberEntity member) {
        return chatRoomJoinRepository.findByMember(member);
    }

    @Transactional(readOnly = true)
    public Long check(String user1,String user2){
        MemberEntity userFirst = memberService.findByNickname(user1);
        List<ChatRoomJoinEntity> listFirst = chatRoomJoinRepository.findByMember(userFirst);
        Set<ChatRoomEntity> setFirst = new HashSet<>();
        for(ChatRoomJoinEntity chatRoomJoin : listFirst){
            setFirst.add(chatRoomJoin.getChatRoom());
        }
        MemberEntity userSecond = memberService.findByNickname(user2);
        List<ChatRoomJoinEntity> listSecond = chatRoomJoinRepository.findByMember(userSecond);
        for(ChatRoomJoinEntity chatRoomJoin : listSecond){
            if(setFirst.contains(chatRoomJoin.getChatRoom())){
                return chatRoomJoin.getChatRoom().getId();
            }
        }
        return 0L;
    }

    @Transactional
    public Long newRoom(String user1, String user2) {
        Long ret = check(user1,user2);
        if(ret != 0){
            //이미 존재하는 방이면 해당 방 번호 리턴
            return ret;
        }
        ChatRoomEntity chatRoom = new ChatRoomEntity();
        ChatRoomEntity newChatRoom = chatRoomRepository.save(chatRoom);
        if(user1.equals(user2)){
            //나 자신과의 채팅은 한명만 존재
            createRoom(user1,newChatRoom);
        }
        else{
            //두명 다 입장
            createRoom(user1,newChatRoom);
            createRoom(user2,newChatRoom);
        }
        return newChatRoom.getId();
    }

    @Transactional
    public void createRoom(String user, ChatRoomEntity chatRoom){
        ChatRoomJoinEntity chatRoomJoin = new ChatRoomJoinEntity(memberService.findByNickname(user),chatRoom);
        chatRoomJoinRepository.save(chatRoomJoin);
    }

    @Transactional(readOnly = true)
    public List<ChatRoomJoinEntity> findByChatRoom(ChatRoomEntity chatRoom) {
        return chatRoomJoinRepository.findByChatRoom(chatRoom);
    }

    @Transactional
    public void delete(ChatRoomJoinEntity chatRoomJoin) {
        chatRoomJoinRepository.delete(chatRoomJoin);
    }

    public String findAnotherUser(ChatRoomEntity chatRoom, String name) {
        List<ChatRoomJoinEntity> chatRoomJoins = findByChatRoom(chatRoom);
        for(ChatRoomJoinEntity chatRoomJoin : chatRoomJoins){
            if(name.equals(chatRoomJoin.getMember().getMemberNickname()) ==false){
                return chatRoomJoin.getMember().getMemberNickname();
            }
        }
        return name;
    }

}
