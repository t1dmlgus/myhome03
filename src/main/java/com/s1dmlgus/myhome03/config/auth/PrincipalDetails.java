package com.s1dmlgus.myhome03.config.auth;


import com.s1dmlgus.myhome03.domain.user.Member;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


@NoArgsConstructor
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Member user;                            // 콤포지션 -> 객체를 품다
    private Map<String, Object> attributes;         // oauth2



    // 일반 사용자
    public PrincipalDetails(Member user) {
        this.user = user;
    }

    // OAuth 로그인
    public PrincipalDetails(Member user, Map<String, Object> attribute) {
        this.user = user;
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

        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
