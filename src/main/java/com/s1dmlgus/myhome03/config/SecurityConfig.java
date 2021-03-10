package com.s1dmlgus.myhome03.config;

import com.s1dmlgus.myhome03.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true)      // 시큐리티 어노테이션 활성화   -> @Secured("ROLE_ADMIN")..등
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;



    @Bean   // 해당 메서드의 리턴되는 오브젝트를 IoC에 등록
    public BCryptPasswordEncoder encoderPwd(){

        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/board/list", "/auth/**", "/css/**", "/js/**", "/img/**")
                .permitAll()
                .antMatchers("/board/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/user/loginForm")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/auth/user/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);


    }
}
