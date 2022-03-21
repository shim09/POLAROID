package com.project.polaroid.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="follow_table")
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "follow_id")
    private Long followId;

    // 팔로잉수
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_my")
    private MemberEntity followMy;

    // 팔로워수
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_your")
    private MemberEntity followYour;

    public static FollowEntity toFollowEntity(MemberEntity myId,MemberEntity yourId){
        FollowEntity followEntity=new FollowEntity();
        followEntity.setFollowMy(myId);
        followEntity.setFollowYour(yourId);
        return followEntity;
    }
}
