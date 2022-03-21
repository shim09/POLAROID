package com.project.polaroid.service;

import com.project.polaroid.dto.MemberAddInfo;
import com.project.polaroid.dto.MemberUpdateDTO;
import com.project.polaroid.entity.MemberEntity;

public interface MemberService {

    // 회원가입
    void memberSave(MemberEntity member);

    // oauth 로그인 추가정보(닉네임,전화번호,주소)
    void memberAddInfo(MemberAddInfo memberAddInfo,Long memberId);

    // 메일 중복체크
    String mailDuplicate(String mail);

    // 닉네임 중복체크
    String nicknameDuplicate(String nickname);

    // 멤버 정보 (마이페이지)
    MemberEntity findById(Long memberId);

    // 회원 정보 변경
    void memberUpdate(MemberUpdateDTO member, Long memberId) throws Exception;

    // 회원탈퇴 처리
    void memberResign(Long id);

    // 채팅 발신자 정보
    MemberEntity findByNickname(String sender);

    // 알람
    void addCount(Long memberId, int messageCount);
}
