package com.project.polaroid.oauth;

import com.project.polaroid.auth.PrincipalDetails;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.oauth.provider.*;
import com.project.polaroid.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // provider(정보제공 사이트) 출력
        System.out.println("userRequest.getClientRegistration() : " + userRequest.getClientRegistration());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 제공 받은 정보
        System.out.println("super.loadUser(userRequest).getAttributes() = " + oAuth2User.getAttributes());

        // 회원가입을 강제로 진행
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            System.out.println("페이스북 로그인 요청");
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            System.out.println("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
            System.out.println("oAuth2User = " + oAuth2User.getAttributes().get("id"));
        } else {
            System.out.println("구글 페이스북 네이버 카카오만 지원");
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        UUID uuid = UUID.randomUUID();
        String password = uuid + "_" + provider;
        String email = oAuth2UserInfo.getEmail() + "_" + provider;
        String role = "ROLE_MEMBER";

        MemberEntity member = memberRepository.findByMemberEmail(email);
        if (member == null) {
            System.out.println("Oauth 로르인이 최초입니다.");
            member = MemberEntity.builder()
                    .memberPw(password)
                    .memberEmail(email)
                    .memberRole(role)
                    .memberProvider(provider)
                    .memberProviderId(providerId)
                    .memberFilename("user_default.png")
                    .memberCheckmail(oAuth2UserInfo.getEmail())
                    .build();
            memberRepository.save(member);
        } else {
            System.out.println("이전에 해당아이디로 자동로그인을 한 적이 있습니다.");
        }
        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
