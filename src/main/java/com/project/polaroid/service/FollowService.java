package com.project.polaroid.service;

import com.project.polaroid.dto.FollowAddDTO;
import com.project.polaroid.entity.FollowEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface FollowService {

    // 팔로워 팔로잉 카운트
    ArrayList<Integer> followCount(Long memberId);

    // 팔로잉 리스트
    List<FollowEntity> followingList(Long memberId);

    // 팔로워 리스트
    List<FollowEntity> followerList(Long memberId);

    // 팔로우 추가
    String followAdd(FollowAddDTO followAdd);

    // 팔로우 취소
    String followDelete(FollowAddDTO followAdd);

    // 팔로우 버튼 체크
    int followCheck(Long myId, Long memberId);
}
