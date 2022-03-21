package com.project.polaroid.config;

import com.project.polaroid.auth.AccessDenied;
import com.project.polaroid.auth.AuthenticationDenied;
import com.project.polaroid.auth.FailHandler;
import com.project.polaroid.oauth.PrincipalOauth2UserService;
import com.project.polaroid.auth.SuccessHandler;
import com.project.polaroid.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity //스프링 시큐리티 필터(SecurityConfig)아래 만든 가 스프링 필터체인에 등록이 됩니다
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) //secured 어노테이션 활성화 , preAuthorize,postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final MemberRepository memberRepository;

    // 패스워드 암호화
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.exceptionHandling()
                        .authenticationEntryPoint(new AuthenticationDenied())
                                .accessDeniedHandler(new AccessDenied());
        http.authorizeRequests()
                .antMatchers("/member/**").authenticated() // 유저페이지 인증(로그인)
                .antMatchers("/seller/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')") // 판매자 권한
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 관리자 권한
                .anyRequest().permitAll() // 이외에 요청은 모두 접근 가능
                .and()
                .formLogin()
                .loginPage("/login") // 권한이 없을때 가게 되는 페이지
                .usernameParameter("memberEmail") // username => memberEmail
                .passwordParameter("memberPw") // password => memberPw
                .loginProcessingUrl("/login") // 해당주소("/login")가 호출되면 대신 로그인을 진행
                .successHandler(new SuccessHandler(memberRepository)) // 로그인 성공시
                .failureHandler(new FailHandler()) //로그인 실패시
                .and()
                .logout()
                .logoutSuccessUrl("/") // 로그아웃시 가게되는 주소
                .and()
                .oauth2Login()
                .successHandler(new SuccessHandler(memberRepository)) // 로그인 성공시
                .loginPage("/login") // 구글로그인시 사용 위 두줄
                .userInfoEndpoint()
                .userService(principalOauth2UserService);//구글 로그인이 완료된 두의 후처리가 필요   Tip. 코드X, 엑세스토큰+사용자 프로필정보(O)
    }

}
