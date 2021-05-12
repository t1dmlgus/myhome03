package com.s1dmlgus.myhome03.api;

import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.reply.ReplyRepository;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.service.MemberService;
import com.s1dmlgus.myhome03.service.ReplyService;
import com.s1dmlgus.myhome03.web.dto.ResponseDto;
import com.s1dmlgus.myhome03.web.dto.reply.ReplyDeleteRequestDto;
import com.s1dmlgus.myhome03.web.dto.reply.ReplyRequestDto;
import com.s1dmlgus.myhome03.web.dto.reply.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Log4j2
@RequiredArgsConstructor
@RestController
public class ReplyApiController {

    public final ReplyService replyService;
    public final MemberService memberService;

    // 댓글 등록
    @PostMapping("/api/v1/reply")
    public ResponseDto<ReplyResponseDto> save(@Valid @RequestBody ReplyRequestDto replyRequestDto, BindingResult bindingResult) {

        System.out.println("replyRequestDto = " + replyRequestDto);


        // 비즈니스 로직
        ReplyResponseDto replyResponseDto = replyService.saveReply(replyRequestDto);

        System.out.println("replyResponseDto = " + replyResponseDto);

        return new ResponseDto<>(HttpStatus.OK.value(),replyResponseDto);
//        return null;
    }

    // 댓글 삭제
    @DeleteMapping("/api/v1/reply/{id}")
    public ResponseDto<Integer> Delete(@RequestBody ReplyDeleteRequestDto replyDeleteRequestDto) {

        System.out.println("replyDeleteRequestDto.getReplyId() = " + replyDeleteRequestDto.getReplyId());
        
        replyService.deleteReply(replyDeleteRequestDto.getReplyId());


        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }




}
