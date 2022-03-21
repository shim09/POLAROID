package com.project.polaroid.repository;

import com.project.polaroid.dto.FollowAddDTO;
import com.project.polaroid.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FollowRepository extends JpaRepository<FollowEntity,Long> {

    // 팔로잉 수
    @Query(value = "SELECT count(a) FROM FollowEntity a WHERE a.followMy.id= :memberId ")
    public int followingCount(Long memberId);

    // 팔로워 수
    @Query(value = "SELECT count(a) FROM FollowEntity a WHERE a.followYour.id= :memberId ")
    public int followerCount(Long memberId);

    // 팔로잉 리스트
    @Query(value = "SELECT a FROM FollowEntity a WHERE a.followMy.id= :memberId ")
    public List<FollowEntity> following(Long memberId);

    // 팔로우 리스트
    @Query(value = "SELECT a FROM FollowEntity a WHERE a.followYour.id= :memberId ")
    public List<FollowEntity> follower(Long memberId);

    // 팔로우 기록
    @Query(value = "SELECT count(a) FROM FollowEntity a WHERE a.followMy.id= :myId and a.followYour.id=:yourId ")
    int find(Long myId, Long yourId);

    // 팔로우 삭제
    @Transactional
    @Modifying
    @Query(value = "delete from follow_table a where a.follow_my= :myId and a.follow_your= :yourId", nativeQuery=true)
    void deleteFollow(Long myId,Long yourId);

}
