package com.project.polaroid.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String,Object> attributes; // oauth2User.getAttributes

    public KakaoUserInfo(Map<String,Object> attributes){
        this.attributes=attributes;
    }


    @Override
    public String getProviderId() {
        int a=(Integer) attributes.get("id");
        return Integer.toString(a);
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        Map<String,Object> map= (Map<String, Object>) attributes.get("kakao_account");
        System.out.println(map);
        return (String) map.get("email");
    }

    @Override
    public String getName() {
        Map<String,Object> map= (Map<String, Object>) attributes.get("kakao_account");
        System.out.println(map);
        return (String) map.get("nickname");
    }
}
