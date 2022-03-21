package com.project.polaroid.controller;

import com.project.polaroid.dto.BoardDetailDTO;
import com.project.polaroid.dto.BoardPagingDTO;
import com.project.polaroid.dto.GoodsDetailDTO;
import com.project.polaroid.entity.BoardEntity;
import com.project.polaroid.entity.FollowEntity;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.page.PagingConstBoard;
import com.project.polaroid.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final MemberService memberService;
    private final IndexService indexService;
    private final JavaMailSender javaMailSender;
    private final FollowService followService;
    private final MemberController memberController;
    private final BoardService boardService;
    private final GoodsService goodsService;
    private final ChatRoomController chatRoomController;

    // 시작 페이지
    @GetMapping({"","/"})
    public String index() {
        return "index";
    }

    // 인증코드 발송
    String sendCode="";
    @PostMapping("/sendMail")
    public @ResponseBody String sendMail(@RequestParam("mail") String mail) {
        Random random=new Random();
        String code="";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail); // 인증코드 받들 사용자 메일 주소
        //인증코드 생성
        for(int i =0; i<3;i++) {
            int index=random.nextInt(25)+65; //A~Z까지 랜덤 알파벳 생성
            code+=(char)index;
        }
        int numIndex=random.nextInt(9999)+1000; //4자리 랜덤 정수를 생성

        code+=numIndex;
        sendCode=code;

        System.out.println("확인용 나중에 삭제 key = " + code);

        message.setSubject("Polaroid 인증번호 입력을 위한 메일 전송");
        message.setText("인증 번호 : "+code);
        javaMailSender.send(message);
        return "ok";
    }

    // 인증코드 확인
    @PostMapping("codeCheck")
    public @ResponseBody String codeCheck(@RequestParam("code") String code){
        if (code.equals(sendCode)) {
            return "ok";
        }
        else {
            return "no";
        }
    }

    // 이메일 중복체크
    @PostMapping("/mailDuplicate")
    public @ResponseBody String mailDuplicate(@RequestParam ("mail") String mail){
        String result=memberService.mailDuplicate(mail);
        return result;
    }

    // 닉네임 중복체크
    @PostMapping({"/nicknameDuplicate","/member/nicknameDuplicate"})
    public @ResponseBody String nicknameDuplicate(@RequestParam ("nickname") String nickname){
        String result=memberService.nicknameDuplicate(nickname);
        return result;
    }

    // 로그인페이지 이동
    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    // 회원가입페이지 이동
    @GetMapping("/join")
    public  String joinForm(){
        return "join";
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String join(MemberEntity member){
        memberService.memberSave(member);
        sendCode=UUID.randomUUID().toString();
        return "redirect:/login";
    }

    // 비밀번호 변경 페이지 이동
    @GetMapping("/lostPassword")
    public String lostPasswordForm(){
        return "lostPassword";
    }

    // 비밀번호 변경 처리
    @PostMapping("/lostPassword")
    public @ResponseBody String lostPassword(@RequestParam ("memberEmail") String memberEmail){
        // 변경할 비밀번호 이메일로 전송
        MemberEntity member=indexService.findPassword(memberEmail);
        String result=null;
        if(member != null) {
            String mail = member.getMemberCheckmail();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mail); // 인증코드 받들 사용자 메일 주소

            String password = UUID.randomUUID().toString();
            ;
            System.out.println("확인용 나중에 삭제 password = " + password);

            message.setSubject("Polaroid 비밀번호 변경");
            message.setText("변경된 비밀번호 : " + password);
            javaMailSender.send(message);

            // 비밀번호 변경
            Long memberId = member.getId();
            indexService.lostPassword(password, memberId);

            result="<script>alert('변경된 이메일을 등록된 이메일에 보냈습니다.');location.href='login'</script>";
        }
        else{
            result="<script>alert('등록되지 않은 아이디 입니다.');location.href='login'</script>";
        }
        return result;
    }

    // 권한없을때
    @GetMapping("/accessDenied")
    public String accessDenied(){
        // alert 처리
        return "accessDenied";
    }

    // 멤버 상세페이지 (팔로워 수, 내 정보)
    @GetMapping("/memberDetail/{memberId}")
    @PreAuthorize("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')" )
    public String mypageForm(@PathVariable Long memberId,
                             HttpSession session, Model model){

        // 헤더 알람
        Long myId = (Long) session.getAttribute("LoginNumber");
        if(myId==memberId){
            return "redirect:http://localhost:8081/member/mypage";
        }
        else {
            if(myId != null)
                model.addAttribute("myNickname",memberService.findById(myId).getMemberNickname());

            // 헤더 알람
            if (myId != null) {
                memberController.notice(myId);
                MemberEntity myMember = memberService.findById(myId);
                model.addAttribute("myMember", myMember);
                model.addAttribute("myId",myId);
            }
            else
                model.addAttribute("myId",-1);
            // 해당멤버 정보
            MemberEntity member = memberService.findById(memberId);
            model.addAttribute("member", member);

            // 팔로우 팔로워 수
            ArrayList<Integer> followCount = followService.followCount(memberId);
            model.addAttribute("follower", followCount.get(0));
            model.addAttribute("following", followCount.get(1));

            // 팔로잉 목록
            List<FollowEntity> following = followService.followingList(memberId);
            model.addAttribute("followingList", following);
            // 팔로워 목록
            List<FollowEntity> follower = followService.followerList(memberId);
            model.addAttribute("followerList", follower);

            // 게시글 수
            List<BoardEntity> boardCount = boardService.boardCount(memberId);
            model.addAttribute("boardCount", boardCount.size());

            // 내 게시글
            List<BoardDetailDTO> boardList = boardService.myPage(memberId);
            model.addAttribute("boardList", boardList);

            // hsq 3.13 추가 좋아요 목록
            List<BoardDetailDTO> likeList = boardService.likeList(memberId);
            model.addAttribute("likeList", likeList);

            // hsw 3.13 추가 찜 목록
            List<GoodsDetailDTO> pickList = goodsService.pickList(memberId);
            model.addAttribute("pickList", pickList);

            Long chatId = chatRoomController.newChat(memberService.findById(myId).getMemberNickname(), member.getMemberNickname());
            model.addAttribute("chatId",chatId);

            int check = followService.followCheck(myId, memberId);
            if (check == 0) {
                model.addAttribute("followButton", 1);
            } else
                model.addAttribute("followButton", 0);

            return "member/memberDetail";
        }
    }


}