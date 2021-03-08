package com.s1dmlgus.myhome03.web.controller;

import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.user.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class UserController {

    // 세션정보 확인하기 -> 일반로그인
    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication,
                                          @AuthenticationPrincipal PrincipalDetails userDetails){


        System.out.println("/test/login[세션정보 확인] ----------------------------------------------------------------------------------------");

        System.out.println("Authentication -> PricipalDetails 다운캐스팅");
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal = " + principal.getUser());

        System.out.println("@AuthenticationPrincipal 어노테이션");
        System.out.println("userDetails.getUser() = " + userDetails.getUser());

        return "세션정보 확인하기";

    }

    // 세션정보 확인하기 -> Oauth2 로그인
    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication,
                                               @AuthenticationPrincipal OAuth2User oauth){

        System.out.println("/test/login[세션정보 확인] ----------------------------------------------------------------------------------------");

        System.out.println("Authentication -> OAuth2User 다운캐스팅");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("principal = " + oAuth2User.getAttributes());


        System.out.println("@AuthenticationPrincipal 어노테이션 ");
        System.out.println("oauth = " + oauth.getAttributes());


        return "Oauth 세션정보 확인하기";

    }

    // 로그인 폼
    @GetMapping("/auth/user/loginForm")
    public String login() {

        return "user/loginForm";
    }

    // 회원가입 폼
    @GetMapping("/auth/user/joinForm")
    public String join(){

        return "user/joinForm";
    }




}
