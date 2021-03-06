package com.s1dmlgus.myhome03.config.auth;


import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.web.dto.member.SessionMember;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/**
 *
 *
 Security Session 안에 오브젝트가 정해져있다. ->Authentication 객체

 Authentication 객체 안에는 UserDetails(User정보)타입 객체가 필요

 *
 *
 */


@NoArgsConstructor
@Data
public class PrincipalDetails implements UserDetails, OAuth2User{

    private Member user;
    private SessionMember sessionMember;                            // 콤포지션 -> 객체를 품다
    private Map<String, Object> attributes;         // oauth2



    // 일반 사용자
    public PrincipalDetails(SessionMember user) {
        this.sessionMember = user;
    }

    // OAuth 로그인
    public PrincipalDetails(SessionMember user, Map<String, Object> attribute) {
        this.sessionMember = user;
        this.attributes = attribute;
    }


    // oauth2 -> override 메소드 --------------------------------------------

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }




    // 유저의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {

                System.out.println("String.valueOf(user.getRole()) = " + String.valueOf(user.getRole()));
                return String.valueOf(user.getRole());
            }

        });
        return collection;
    }

    @Override
    public String getPassword() {

        return sessionMember.getPassword();
    }

    @Override
    public String getUsername() {
        return sessionMember.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
