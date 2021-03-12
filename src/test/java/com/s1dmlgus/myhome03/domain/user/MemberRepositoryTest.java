package com.s1dmlgus.myhome03.domain.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 멤버findAll(){

        List<Member> all = memberRepository.findAll();

        for (Member member : all) {
            System.out.println("member = " + member.boards);
        }
    }


}