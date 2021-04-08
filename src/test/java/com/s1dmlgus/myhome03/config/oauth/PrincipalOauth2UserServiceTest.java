//package com.s1dmlgus.myhome03.config.oauth;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.support.DefaultListableBeanFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class PrincipalOauth2UserServiceTest {
//
//    @Autowired
//    DefaultListableBeanFactory df;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//
//    @Test
//    public void bean(){
//
//        for (String beanDefinitionName : df.getBeanDefinitionNames()) {
//            System.out.println("name22 = " + df.getBean(beanDefinitionName).getClass().getName());
//        }
//    }
//
//
//    @Test
//    public void st(){
//
//        String AA = "SGDD";
//
//        System.out.println("AA = " + AA);
//
//        String ss = bCryptPasswordEncoder.encode(AA);
//
//        System.out.println("AA2 = " + ss);
//
//    }
//
//}