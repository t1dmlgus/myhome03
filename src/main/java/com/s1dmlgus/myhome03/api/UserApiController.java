package com.s1dmlgus.myhome03.api;




import com.s1dmlgus.myhome03.service.MemberService;
import com.s1dmlgus.myhome03.web.dto.ResponseDto;
import com.s1dmlgus.myhome03.web.dto.member.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RequiredArgsConstructor
@RestController
public class UserApiController {


    private final MemberService memberService;


    // 등록
    @PostMapping("/auth/user/join")
    public ResponseDto<?> save(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult) {


        // Id 중복확인
        boolean checkId = memberService.validId(memberRequestDto.getUsername());
        if (checkId) {
            log.info("유저 아이디 확인 = " + checkId);
            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), "현재 사용중인 아이디입니다.");
        }


        /* 핵심기능 */

        System.out.println("memberRequestDto = " + memberRequestDto);
        memberService.saveMember(memberRequestDto);

        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

}
