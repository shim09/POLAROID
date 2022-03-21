package com.project.polaroid.entity;

import com.project.polaroid.dto.MemberUpdateDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="member_table")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "member_id")
    private Long id;

    @Column(unique = true, length = 50)
    private String memberEmail;

    // 암호화되어 저장되어 길이가 김
    @Column(length = 70)
    private String memberPw;

    @Column(unique = true, length = 20)
    private String memberNickname;

    @Column(length = 11)
    private String memberPhone;

    @Column(length = 100)
    private String memberAddress;

    @Column(length = 100)
    private String memberFilename;

    @Column(length = 20)
    private String memberRole;

    @Column(length = 20)
    private String memberProvider;

    @Column(length = 50)
    private String memberProviderId;

    @Column(length = 30)
    private String  memberCheckmail;

    @Column
    private int memberMessage;

    @Column
    private int memberFollow;

    // 팔로우 테이블
    @OneToMany(mappedBy = "followMy", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    private List<FollowEntity> memberFollowMy = new ArrayList<>();
    @OneToMany(mappedBy = "followYour", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    private List<FollowEntity> memberFollowYour = new ArrayList<>();

    // 판매자 권한 테이블
    @OneToMany(mappedBy = "memberId", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    private List<SellerEntity> sellerEntityList = new ArrayList<>();

    // 보드 테이블
    @OneToMany(mappedBy = "memberId", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "memberId", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "memberId", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    private List<LikeEntity> likeEntityList = new ArrayList<>();

    // 굿즈 테이블
    @OneToMany(mappedBy = "memberId", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    private List<GoodsCommentEntity> goodsEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "goodsWriter", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    private List<GoodsEntity> goodsCommentEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    private List<GoodsLikeEntity> goodsLikeEntityList = new ArrayList<>();

    // 채팅 테이블
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    private List<ChatMessageEntity> chatMessageEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    private List<ChatRoomJoinEntity> chatRoomJoinEntityList = new ArrayList<>();

    @Builder
    public MemberEntity(String memberEmail, String memberPw, String memberFilename, String memberRole, String memberProvider, String memberProviderId, String memberCheckmail) {
        this.memberEmail = memberEmail;
        this.memberPw = memberPw;
        this.memberFilename = memberFilename;
        this.memberRole = memberRole;
        this.memberProvider = memberProvider;
        this.memberProviderId = memberProviderId;
        this.memberCheckmail = memberCheckmail;
    }

    public static MemberEntity UpdateDTOtoEntity(MemberUpdateDTO memberUpdateDTO){
        MemberEntity member=new MemberEntity();
        member.setMemberFilename(memberUpdateDTO.getMemberFilename());
        member.setMemberAddress(memberUpdateDTO.getMemberAddress());
        member.setMemberNickname(memberUpdateDTO.getMemberNickname());
        member.setMemberPhone(memberUpdateDTO.getMemberPhone());
        return member;
    }


}
