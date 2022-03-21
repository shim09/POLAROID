package com.project.polaroid.service;

import com.project.polaroid.dto.MemberAddInfo;
import com.project.polaroid.dto.MemberUpdateDTO;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    // 패스워드 암호화
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원가입
    @Override
    public void memberSave(MemberEntity member) {
        member.setMemberRole("ROLE_MEMBER");
        // 회원 프로필 기본화
        member.setMemberFilename("user_default.png");
        // 세줄 패스워드 암호화 과정
        String rawPassword=member.getMemberPw();
        String encPassword=bCryptPasswordEncoder.encode(rawPassword);
        member.setMemberPw(encPassword);

        memberRepository.save(member);
    }

    // oauth 로그인 추가정보(닉네임,전화번호,주소)
    @Override
    @Transactional
    public void memberAddInfo(MemberAddInfo memberAddInfo,Long memberId) {

        memberRepository.addInfo(memberAddInfo.getMemberAddress(),memberAddInfo.getMemberPhone(),memberAddInfo.getMemberNickname(),memberId);
    }

    // 아이디 중복체크
    @Override
    public String mailDuplicate(String mail) {
         MemberEntity member=memberRepository.findByMemberEmail(mail);
        if (member==null)
            return "ok";
        else
            return "no";
    }

    // 닉네임 중복체크
    @Override
    public String  nicknameDuplicate(String nickname) {
        MemberEntity member = memberRepository.findByMemberNickname(nickname);
        if (member==null)
            return "ok";
        else
            return "no";
    }

    // 멤버 정보 (마이페이지)
    @Override
    public MemberEntity findById(Long memberId) {
        return (memberRepository.findById(memberId)).get();
    }

    // 회원정보 변경
    @Override
    @Transactional
    public void memberUpdate(MemberUpdateDTO member, Long memberId) throws Exception{
        if (!member.getMemberFile().isEmpty()) {
            MultipartFile file = member.getMemberFile();
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();

            String filePath = System.getProperty("user.dir") + "/src/main/resources/static/profile";

            File saveFile = new File(filePath, fileName);
            file.transferTo(saveFile);

            member.setMemberFilename(fileName);
        }
        else{
            member.setMemberFilename("user_default.png");
        }
        MemberEntity memberEntity=MemberEntity.UpdateDTOtoEntity(member);
        memberRepository.memberUpdate(memberEntity.getMemberAddress(),memberEntity.getMemberPhone(),memberEntity.getMemberNickname(),memberEntity.getMemberFilename(),memberId);
    }

    // 회원탈퇴 처리
    @Override
    public void memberResign(Long id) {
        memberRepository.deleteById(id);
    }

    // 채팅 발신자 정보
    @Override
    public MemberEntity findByNickname(String sender) {
        return memberRepository.findByMemberNickname(sender);
    }

    // 알람
    @Override
    @Transactional
    public void addCount(Long memberId, int messageCount) {
        memberRepository.addCount(memberId,messageCount);
    }

}
