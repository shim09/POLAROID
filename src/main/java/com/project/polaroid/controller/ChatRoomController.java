package com.project.polaroid.controller;

import com.project.polaroid.auth.PrincipalDetails;
import com.project.polaroid.dto.ChatRoomFormDTO;
import com.project.polaroid.entity.ChatMessageEntity;
import com.project.polaroid.entity.ChatRoomEntity;
import com.project.polaroid.entity.ChatRoomJoinEntity;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.repository.NoticeRepository;
import com.project.polaroid.service.ChatMessageService;
import com.project.polaroid.service.ChatRoomJoinService;
import com.project.polaroid.service.ChatRoomService;
import com.project.polaroid.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final NoticeRepository noticeRepository;
    private final MemberService memberService;
    private final ChatRoomJoinService chatRoomJoinService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final MemberController memberController;

    // 닉네임 체크
    @GetMapping("/users/nameChk/{name}")
    public @ResponseBody String nameChk(@PathVariable("name") String name){
        if(memberService.findByNickname(name)!= null){
            return "success";
        }
        return "false";
    }

    // 채팅방 목록
    @PreAuthorize("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')" )
    @GetMapping("/chat")
    public String chatHome(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        // 헤더
        memberController.notice(principalDetails.getMember().getId());
        model.addAttribute("member",memberService.findById(principalDetails.getMember().getId()));

        model.addAttribute("nickname",principalDetails.getMember().getMemberNickname());

        // 채팅방 목록
        MemberEntity member = memberService.findByNickname(principalDetails.getMember().getMemberNickname());
        List<ChatRoomJoinEntity> chatRoomJoins = chatRoomJoinService.findByUser(member);
        List<ChatRoomFormDTO> chatRooms = chatRoomService.setting(chatRoomJoins,member);

        // 채팅방 노티스
        for(ChatRoomFormDTO c: chatRooms){
                int count =noticeRepository.countRoom(principalDetails.getMember().getId(),c.getId());
                c.setMessageNotice(count);
        }

        model.addAttribute("chatRooms",chatRooms);

        model.addAttribute("nickname",principalDetails.getMember().getMemberNickname());
        for(ChatRoomFormDTO c: chatRooms){
            MemberEntity byId=memberService.findById(c.getId());
            c.setMember(byId);
        }
        if (member == null){
            model.addAttribute("memberName", "");
            model.addAttribute("memberId",0);
        } else {
            model.addAttribute("memberName",member.getMemberNickname());
            model.addAttribute("memberId",member.getId());
        }

        return "chat/main";

    }

    // 채팅방 생성
    @PreAuthorize("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')" )
    @PostMapping("/chat/newChat")
    public Long newChat(@RequestParam("receiver") String user1, @RequestParam("sender") String user2){
        Long chatRoomId = chatRoomJoinService.newRoom(user1,user2);
        return chatRoomId;
    }

    // 채팅방 입장
//    @PreAuthorize("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')" )
//    @RequestMapping("/personalChat/{chatRoomId}")
//    public String goChat(@PathVariable("chatRoomId") Long chatRoomId,Model model
//            ,@AuthenticationPrincipal PrincipalDetails principalDetails){
//        // ChatRoomEntity 메시지 목록 -> 리스트
//        Optional<ChatRoomEntity> opt = chatRoomService.findById(chatRoomId);
//        ChatRoomEntity chatRoom = opt.get();
//        List<ChatMessageEntity> messages = chatRoom.getMessages();
//
//        Collections.sort(messages, (t1, t2) -> {
//            if(t1.getId() > t2.getId()) return 1;
//            else return -1;
//        });
//
//        if (principalDetails.getMember() == null){
//            model.addAttribute("memberName", "");
//            model.addAttribute("memberId", 0);
//        }else {
//            model.addAttribute("MemberName", principalDetails.getMember().getMemberNickname());
//            model.addAttribute("memberId", principalDetails.getMember().getId());
//        }
//
//
//        model.addAttribute("userName",principalDetails.getMember().getMemberNickname());
//        model.addAttribute("userId",principalDetails.getMember().getId());
//
//        model.addAttribute( "messages",messages);
//        model.addAttribute("nickname",principalDetails.getMember().getMemberNickname());
//        model.addAttribute("chatRoomId",chatRoomId);
//
//        chatMessageService.deleteCount(chatRoomId,principalDetails.getMember().getId());
//        int cnt = 0;
//        List<ChatRoomJoinEntity> list = chatRoomJoinService.findByChatRoom(chatRoom);
//        for(ChatRoomJoinEntity join : list){
//            if(join.getMember().getMemberNickname().equals(principalDetails.getMember().getMemberNickname()) == false){
//                model.addAttribute("receiver",join.getMember().getMemberNickname());
//                ++cnt;
//            }
//        }
//        if(cnt >= 2){
//            return "redirect:/chat";
//        }
//        if(cnt == 0){
//            model.addAttribute("receiver","");
//        }
//        return "chat/chatRoom";
//    }

    @PreAuthorize("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')" )
    @RequestMapping("/personalChat/{chatRoomId}")
    public String goChat(@PathVariable("chatRoomId")Long chatRoomId, Model model,
                         @AuthenticationPrincipal PrincipalDetails principalDetails){
        Optional<ChatRoomEntity> opt = chatRoomService.findById(chatRoomId);
        ChatRoomEntity chatRoom = opt.get();
        List<ChatMessageEntity> messages = chatRoom.getMessages();
        Collections.sort(messages, (t1, t2) -> {
            if (t1.getId() > t2.getId()) return 1;
            else return -1;
        });
        if (principalDetails.getMember() == null){
            model.addAttribute("memberName", "");
            model.addAttribute("memberId", 0);
        }else {
            model.addAttribute("MemberName", principalDetails.getMember().getMemberNickname());
            model.addAttribute("memberId", principalDetails.getMember().getId());
        }
        List<ChatRoomJoinEntity> list = chatRoomJoinService.findByChatRoom(chatRoom);
        model.addAttribute("messages", messages);
        model.addAttribute("nickname",principalDetails.getMember().getMemberNickname());
        model.addAttribute("profile",principalDetails.getMember().getMemberFilename());
        model.addAttribute("chatRoomId",chatRoomId);

       chatMessageService.deleteCount(chatRoomId,principalDetails.getMember().getId());

        int cnt = 0;
        for (ChatRoomJoinEntity join : list){
            if (join.getMember().getMemberNickname().equals(principalDetails.getMember().getMemberNickname()) == false){
                model.addAttribute("receiver", join.getMember().getMemberNickname());
                ++cnt;
            }
        }
        if (cnt >= 2){
            return "redirect:/chat";
        }
        if (cnt == 0){
            model.addAttribute("receiver", "");
        }
        return "chat/chatRoom";
    }

}
