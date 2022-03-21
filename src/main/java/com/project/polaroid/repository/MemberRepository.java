package com.project.polaroid.repository;

import com.project.polaroid.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository <MemberEntity,Long> {

    public MemberEntity findByMemberEmail(String email);
    public MemberEntity findByMemberNickname(String nickname);


    // oauth 추가정보 입력
    @Modifying
    @Query("update MemberEntity p set p.memberAddress = :address , p.memberPhone = :phone , p.memberNickname = :nickname where p.id = :id")
    void addInfo(String address, String phone,String nickname,Long id);

    // 비밀번호 변경
    @Modifying
    @Query("update MemberEntity p set p.memberPw = :memberPw where p.id = :id")
    void lostPassword(String memberPw,Long id);

    // 판매자 권한 부여
    @Modifying
    @Query("update MemberEntity p set p.memberRole = 'ROLE_SELLER' where p.id = :memberId")
    void giveRole(Long memberId);

    // 회원정보 변경
    @Modifying
    @Query("update MemberEntity p set p.memberAddress = :memberAddress  , p.memberPhone = :memberPhone , p.memberNickname = :memberNickname, p.memberFilename= :memberFilename where p.id = :id")
    void memberUpdate(String memberAddress, String memberPhone, String memberNickname, String memberFilename, Long id);

    // 알람
    @Modifying
    @Query("update MemberEntity p set  p.memberMessage = :messageCount where p.id = :memberId")
    void addCount(Long memberId, int messageCount);

    List<MemberEntity> findAllByOrderByIdDesc();
}
