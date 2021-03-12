package com.s1dmlgus.myhome03.api;

import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.reply.ReplyRepository;
import com.s1dmlgus.myhome03.service.ReplyService;
import com.s1dmlgus.myhome03.web.dto.ResponseDto;
import com.s1dmlgus.myhome03.web.dto.reply.ReplyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
public class ReplyApiController {

    public final ReplyService replyService;


    // 등록
    @PostMapping("/api/v1/reply")
    public ResponseDto<Integer> save(@Valid @RequestBody ReplyRequestDto replyRequestDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails userDetails){


        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

}
