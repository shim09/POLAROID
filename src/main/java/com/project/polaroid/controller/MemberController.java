package com.project.polaroid.controller;

import com.project.polaroid.auth.PrincipalDetails;
import com.project.polaroid.dto.*;
import com.project.polaroid.entity.BoardEntity;
import com.project.polaroid.entity.FollowEntity;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.page.PagingConstBoard;
import com.project.polaroid.repository.MemberRepository;
import com.project.polaroid.repository.NoticeRepository;
import com.project.polaroid.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final FollowService followService;
    private final SellerRoleService sellerRoleService;
    private final JavaMailSender javaMailSender;
    private final NoticeRepository noticeRepository;
    private final ChatMessageService chatMessageService;
    private final BoardService boardService;
    private final GoodsService goodsService;

    @GetMapping("/addInfo")
    public String addInfoForm(){
        return "member/addInfo";
    }

    // oauth로그인시 추가정보
    @PostMapping("/addInfo")
    public String addInfo(@ModelAttribute MemberAddInfo memberAddInfo,
                          @AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails.getMember().getId() = " + principalDetails.getMember().getId());
        memberService.memberAddInfo(memberAddInfo,principalDetails.getMember().getId());
        return "index";
    }

    // hsw 3.13 수정 마이페이지 출력 (팔로워 수, 내 정보)
    @GetMapping("/mypage")
    public String mypageForm(@AuthenticationPrincipal PrincipalDetails principalDetails,
                             Model model){

        model.addAttribute("status",false);

        // 알림 처리
        notice(principalDetails.getMember().getId());
        MemberEntity member=memberService.findById(principalDetails.getMember().getId());
        model.addAttribute("member",member);

        // 팔로우 팔로잉 수
        ArrayList<Integer> followCount=followService.followCount(principalDetails.getMember().getId());
        model.addAttribute("follower",followCount.get(0));
        model.addAttribute("following",followCount.get(1));

        // 팔로잉 목록
        List<FollowEntity> following = followService.followingList(principalDetails.getMember().getId());
        model.addAttribute("followingList",following);
        // 팔로워 목록
        List<FollowEntity> follower = followService.followerList(principalDetails.getMember().getId());
        model.addAttribute("followerList",follower);

        // 게시글 수
        List<BoardEntity> boardCount=boardService.boardCount(principalDetails.getMember().getId());
        model.addAttribute("boardCount",boardCount.size());

        // 내 게시글
        List<BoardDetailDTO> boardList=boardService.myPage(principalDetails.getMember().getId());
        model.addAttribute("boardList",boardList);

        // hsq 3.13 추가 좋아요 목록
        List<BoardDetailDTO> likeList=boardService.likeList(principalDetails.getMember().getId());
        model.addAttribute("likeList",likeList);

        // hsw 3.13 추가 찜 목록
        List<GoodsDetailDTO> pickList=goodsService.pickList(principalDetails.getMember().getId());
        model.addAttribute("pickList",pickList);

        System.out.println("MemberController.mypageForm");
        return "member/myPage";
    }

    // 판매자 권한신청
    @GetMapping("/sellerRole")
    public @ResponseBody String sellerRole(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("MemberController.sellerRole");
        String duplicate=sellerRoleService.save(principalDetails.getMember().getId());
        String result=null;
        if(duplicate=="ok"){
            result="<script>alert('판매자권한 신청이 완료되었습니다.');location.href='mypage'</script>";
        }
        else {
            result="<script>alert('이미 판매자권한을 신청 하셨습니다.');location.href='mypage'</script>";
        }
        return result;
    }

    // 본인 확인페이지
    @GetMapping("/selfAuthentication")
   public String selfAuthentication (){
        return "member/selfAuthentication";
    }

    // 본인인증코드 발송
    String sendCode="";
    @PostMapping("/submitCode")
    public @ResponseBody String submitCode(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("MemberController.submitCode");
        MemberEntity member=memberService.findById(principalDetails.getMember().getId());
        String mail=member.getMemberCheckmail();
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

        System.out.println("MemberController.submitCode");
        System.out.println("확인용 나중에 삭제 key = " + code);

        message.setSubject("본인 확인을 위한 인증코드 메일 전송");
        message.setText("인증 번호 : "+code);
        javaMailSender.send(message);
        return "ok";
    }

    // 본인확인 인증코드 확인
    @PostMapping("codeCheck")
    public @ResponseBody String codeCheck(@RequestParam("code") String code){
        if (code.equals(sendCode)) {
            return "ok";
        }
        else {
            return "no";
        }
    }

    // 본인확인 -> 수정페이지
    @PostMapping("selfAuthentication")
    public String selfAuthentication(@AuthenticationPrincipal PrincipalDetails principalDetails,Model model,HttpSession session){
        // 헤더
        Long memberId = (Long)session.getAttribute("LoginNumber");
        notice(memberId);
        model.addAttribute("member",memberService.findById(memberId));

        sendCode= UUID.randomUUID().toString();
        return "member/update";
    }

    // 회원정보 변경
    @PostMapping("/update")
    public String memberUpdate(@ModelAttribute MemberUpdateDTO member,@AuthenticationPrincipal PrincipalDetails principalDetails ) throws Exception{
        memberService.memberUpdate(member,principalDetails.getMember().getId());
        return "index";
    }

    // 회원탈퇴 페이지
    @GetMapping("/resign")
    public String memberResignForm(@AuthenticationPrincipal PrincipalDetails principalDetails,Model model){
        notice(principalDetails.getMember().getId());
        MemberEntity member=memberService.findById(principalDetails.getMember().getId());
        model.addAttribute("member",member);
        return "member/resign";
    }

    //회원탈퇴 처리
    @PostMapping("/resign")
    public String memberResign(@AuthenticationPrincipal PrincipalDetails principalDetails){
        memberService.memberResign(principalDetails.getMember().getId());
        return "redirect:http://localhost:8081/logout";
    }


    // 알림 처리
    public void notice(Long memberId){
        System.out.println("memberId = " + memberId);
        System.out.println("MemberController.notice");
        int messageCount = chatMessageService.count(memberId);
        System.out.println("messageCount = " + messageCount);
        memberService.addCount(memberId,messageCount);
    }

    // 관리자 회원 삭제
    @DeleteMapping("{memberId}")
    public ResponseEntity deleteById(@PathVariable Long memberId) {
        System.out.println("memberId = " + memberId);
        memberService.memberResign(memberId);
        return new ResponseEntity(HttpStatus.OK);
    }

    // hsw 3.13 좋아요 목록
    @GetMapping("/likeListShow")
    public String likeList(@AuthenticationPrincipal PrincipalDetails principalDetails,Model model){
        String s = mypageForm(principalDetails, model);
        model.addAttribute("status",true);
        return s;
    }
}
