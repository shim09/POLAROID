package com.project.polaroid.service;

import com.project.polaroid.dto.FollowAddDTO;
import com.project.polaroid.entity.FollowEntity;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final MemberService memberService;

    // 팔로워 팔로잉 카운트
    @Override
    @Transactional
    public ArrayList<Integer> followCount(Long memberId) {
        int follower =followRepository.followerCount(memberId);
        int following =followRepository.followingCount(memberId);
        ArrayList<Integer> followCount=new ArrayList<>();
        followCount.add(follower);
        followCount.add(following);
        return followCount;
    }

    // 팔로윙 리스트
    @Override
    @Transactional
    public List<FollowEntity> followingList(Long memberId) {
        return followRepository.following(memberId);
    }

    // 팔로워 리스트
    @Override
    @Transactional
    public List<FollowEntity> followerList(Long memberId) {
        return followRepository.follower(memberId);
    }

    // 팔로우 추가
    @Override
    @Transactional
    public String followAdd(FollowAddDTO followAdd) {
        if(followRepository.find(followAdd.getMyId(),followAdd.getYourId()) == 0){
            MemberEntity my=memberService.findById(followAdd.getMyId());
            MemberEntity your=memberService.findById(followAdd.getYourId());
            followRepository.save(FollowEntity.toFollowEntity(my,your));
            return "팔로우 되었습니다.";
        }
        else {
            return "이미 팔로우한 아이디 입니다.";
        }
    }

    // 팔로우 삭제
    @Override
    @Transactional
    public String followDelete(FollowAddDTO followAddDTO){
        Long myId=followAddDTO.getMyId();
        Long yourId=followAddDTO.getYourId();
        followRepository.deleteFollow(myId,yourId);
        return "삭제 되었습니다.";
    }

    // 팔로우 버튼을 위찬 체크
    @Override
    public int followCheck(Long myId, Long memberId) {
        return followRepository.find(myId,memberId);
    }

}
