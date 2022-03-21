package com.project.polaroid.auth;

import com.project.polaroid.entity.MemberEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
@Transactional
public class PrincipalDetails implements UserDetails, OAuth2User {

    private MemberEntity member;
    private Map<String,Object> attributes;

    // 일반 로그인시 사용하는 생성자
    public PrincipalDetails (MemberEntity member){
        this.member=member;
    }

    //OAuth2 로그인시 사용하는 생성자
    public PrincipalDetails(MemberEntity member, Map<String,Object> attributes) {
        this.member=member;
        this.attributes=attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 해당 유저 권한 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getMemberRole();
            }
        });
        return collect;
    }

    // 추가 정보 입력을 위한 메소드
    public String getPhone(){
        return member.getMemberPhone();
    }

    // 비밀번호
    @Override
    public String getPassword() {
        return member.getMemberPw();
    }

    // 이메일
    @Override
    public String getUsername() {
        return member.getMemberEmail();
    }

    // 계정 만료 => false , 만료 X=> true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 => true , 잠김 X => true
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 기간 만료 => false , 만료 X => true
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 비활성화 => true , 활성화 true
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

}
