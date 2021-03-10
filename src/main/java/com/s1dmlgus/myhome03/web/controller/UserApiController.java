package com.s1dmlgus.myhome03.web.controller;

import com.s1dmlgus.myhome03.service.MemberService;
import com.s1dmlgus.myhome03.web.dto.ResponseDto;
import com.s1dmlgus.myhome03.web.dto.member.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final MemberService memberService;


    // 등록
    @PostMapping("auth/user/join")
    public ResponseDto<?> save(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult) {



        /* 핵심기능 */

        System.out.println("memberRequestDto = " + memberRequestDto);
        memberService.saveMember(memberRequestDto);

        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }


}
