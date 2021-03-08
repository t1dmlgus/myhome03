package com.s1dmlgus.myhome03.config.auth;

import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.domain.user.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 *  시큐리티 설정에서 loginProcessingUrl("/login");
 *  /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 
 *  loadUserByUsername 함수 실행
 *
 */

@Service    // IoC로 띄움
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("username131 = " + username);
        Member userEntity = memberRepository.findByUsername(username);

        System.out.println("UserDetailsService. user = " + userEntity);



        // PrincipalDetails(userEntity) 생성되서 리턴될 때
        // session( Authentication ( UserDetails)) 형성된다.

        return new PrincipalDetails(userEntity);
    }

}
