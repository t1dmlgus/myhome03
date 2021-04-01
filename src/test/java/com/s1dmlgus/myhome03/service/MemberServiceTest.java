package com.s1dmlgus.myhome03.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MemberServiceTest {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    public void st(){

        String AA = "SGDD";

        System.out.println("AA = " + AA);

        String ss = bCryptPasswordEncoder.encode(AA);

        System.out.println("AA2 = " + ss);

    }

}