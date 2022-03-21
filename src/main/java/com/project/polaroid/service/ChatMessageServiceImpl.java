package com.project.polaroid.service;

import com.project.polaroid.dto.ChatMessageFormDTO;
import com.project.polaroid.entity.ChatMessageEntity;
import com.project.polaroid.entity.NoticeEntity;
import com.project.polaroid.repository.ChatMessageRepository;
import com.project.polaroid.repository.MemberRepository;
import com.project.polaroid.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService{

    private final ChatMessageRepository chatMessageRepository;
    private final MemberService memberService;
    private final ChatRoomService chatRoomService;
    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;

    @Transactional
    public void save(ChatMessageFormDTO message) {
        ChatMessageEntity chatMessage = new ChatMessageEntity(message.getMessage(), LocalDateTime.now(),chatRoomService.findById(message.getChatRoomId()).get()
                ,memberService.findByNickname(message.getSender()));
        chatMessageRepository.save(chatMessage);

        Long memberId = memberRepository.findByMemberNickname(message.getReceiver()).getId();

        NoticeEntity noticeEntity=new NoticeEntity(memberId,message.getChatRoomId(),"message");
        noticeRepository.save(noticeEntity);


    }

    @Override
    @Transactional
    public int count(Long memberId) {
        return noticeRepository.countMessage(memberId,"message");
    }

    @Override
    @Transactional
    public void deleteCount(Long chatRoomId, Long memberId) {
        noticeRepository.deleteCount(chatRoomId,memberId,"message");
    }


}
