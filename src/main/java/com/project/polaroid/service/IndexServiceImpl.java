package com.project.polaroid.service;

import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService{

    // 패스워드 암호화
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public MemberEntity findPassword(String memberEmail) {
        MemberEntity member = memberRepository.findByMemberEmail(memberEmail);
        return member;
    }

    @Override
    @Transactional
    public void lostPassword(String password,Long memberId) {
        String encPassword=bCryptPasswordEncoder.encode(password);

        memberRepository.lostPassword(encPassword,memberId);
    }
}
