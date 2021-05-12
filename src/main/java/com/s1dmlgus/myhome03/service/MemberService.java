package com.s1dmlgus.myhome03.service;


import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.domain.user.MemberRepository;
import com.s1dmlgus.myhome03.domain.user.Role;
import com.s1dmlgus.myhome03.web.dto.member.MemberRequestDto;
import com.s1dmlgus.myhome03.web.dto.member.MemberResponseDto;
import com.s1dmlgus.myhome03.web.dto.member.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    public final MemberRepository memberRepositsory;

    public final BCryptPasswordEncoder bCryptPasswordEncoder;



    // 유저 등록
    @Transactional
    public void saveMember(MemberRequestDto memberRequestDto){



        String rowPassword = memberRequestDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rowPassword);


        Member member = memberRequestDto.toEntity();
        member.encodePassword(encPassword);
        member.setRole(Role.ROLE_USER);
        member.setProvider("default");

        System.out.println("member = " + member);

        memberRepositsory.save(member);


    }

    // 유저 조회
    @Transactional
    public MemberResponseDto findById(Long id) {



        Member findMember = memberRepositsory.findById(id).orElseThrow(() -> {

            return new IllegalArgumentException("해당 멤바가 없습니다");
        });

        return new MemberResponseDto(findMember);
    }

    // 세션 -> MemberDto
    @Transactional
    public Member findSessionMember(SessionMember sessionMember) {

        if (sessionMember != null) {
            Long id = sessionMember.getId();
            Member member = memberRepositsory.findById(id).orElseThrow(() -> {
                return new IllegalArgumentException("해당 세션 id의 유저가 업습니02당");

            });

            return member;
        }

        return null;
    }

    // ID 중복 체크
    @Transactional
    public boolean validId(String username) {
        return memberRepositsory.existsByUsername(username);
    }
}
