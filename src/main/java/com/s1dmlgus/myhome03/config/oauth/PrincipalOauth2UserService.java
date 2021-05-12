package com.s1dmlgus.myhome03.config.oauth;

import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.domain.user.MemberRepository;
import com.s1dmlgus.myhome03.domain.user.Role;
import com.s1dmlgus.myhome03.web.dto.member.SessionMember;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Log4j2
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


    @Autowired
    DefaultListableBeanFactory df;

    @Autowired
    private MemberRepository memberRepository;

//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;


    // 구글로 받은 userRequest 데이터에 대한 후처리 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("userRequest.getClientRegistration() = " + userRequest.getClientRegistration());
        System.out.println("userRequest.getAccessToken() = " + userRequest.getAccessToken());
        System.out.println("userRequest.getClientRegistration().toString() = " + userRequest.getClientRegistration().toString());
        System.out.println("super.loadUser(userRequest) = " + super.loadUser(userRequest).getAttributes());


        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());



//        for (String beanDefinitionName : df.getBeanDefinitionNames()) {
//            System.out.println("name22 = " + df.getBean(beanDefinitionName).getClass().getName());
//        }



        // 강제 회원가입

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String username = oAuth2User.getAttribute("name");
//        String password = bCryptPasswordEncoder.encode("s1dmlgus");
        String password = "s1dmlgus";
        String email = oAuth2User.getAttribute("email");
        Role role = Role.ROLE_USER;


        Member userEntity = memberRepository.findByUsername(username);

        if (userEntity == null) {
            Member createMember = Member.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();

            userEntity = memberRepository.save(createMember);

        }

        log.info("userEntity" + userEntity);



        return new PrincipalDetails(new SessionMember(userEntity), oAuth2User.getAttributes());
        //return null;
    }
}
