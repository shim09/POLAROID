package com.project.polaroid.service;

import com.project.polaroid.dto.ChatRoomFormDTO;
import com.project.polaroid.entity.ChatMessageEntity;
import com.project.polaroid.entity.ChatRoomEntity;
import com.project.polaroid.entity.ChatRoomJoinEntity;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomJoinService chatRoomJoinService;

    @Transactional(readOnly = true)
    public Optional<ChatRoomEntity> findById(Long id) {
        return chatRoomRepository.findById(id);
    }

    public List<ChatRoomFormDTO> setting(List<ChatRoomJoinEntity> chatRoomJoins, MemberEntity user) {
        List<ChatRoomFormDTO> chatRooms = new ArrayList<>();
        for(ChatRoomJoinEntity tmp : chatRoomJoins){
            ChatRoomFormDTO chatRoomForm = new ChatRoomFormDTO();
            ChatRoomEntity chatRoom = tmp.getChatRoom();
            chatRoomForm.setId(chatRoom.getId());
            if(chatRoom.getMessages().size() != 0) {
                Collections.sort(chatRoom.getMessages(), new Comparator<ChatMessageEntity>() {
                    @Override
                    public int compare(ChatMessageEntity c1, ChatMessageEntity c2) {
                        if(c1.getTime().isAfter(c2.getTime())){
                            return -1;
                        }
                        else{
                            return 1;
                        }
                    }
                });
                ChatMessageEntity lastMessage = chatRoom.getMessages().get(0);
                chatRoomForm.makeChatRoomForm(lastMessage.getMessage(),chatRoomJoinService.findAnotherUser(chatRoom, user.getMemberNickname()),lastMessage.getTime());
                chatRooms.add(chatRoomForm);
            }
            else{
                chatRoomJoinService.delete(tmp);
            }
        }
        Collections.sort(chatRooms, new Comparator<ChatRoomFormDTO>() {
            @Override
            public int compare(ChatRoomFormDTO c1, ChatRoomFormDTO c2) {
                if(c1.getTime().isAfter(c2.getTime())){
                    return -1;
                }
                else{
                    return 1;
                }
            }
        });
        return chatRooms;
    }
}
