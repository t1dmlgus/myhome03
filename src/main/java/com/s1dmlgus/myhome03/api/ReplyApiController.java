package com.s1dmlgus.myhome03.api;

import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.reply.ReplyRepository;
import com.s1dmlgus.myhome03.service.ReplyService;
import com.s1dmlgus.myhome03.web.dto.ResponseDto;
import com.s1dmlgus.myhome03.web.dto.reply.ReplyDeleteRequestDto;
import com.s1dmlgus.myhome03.web.dto.reply.ReplyRequestDto;
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


    // 댓글 등록
    @PostMapping("/api/v1/reply")
    public ResponseDto<Integer> save(@Valid @RequestBody ReplyRequestDto replyRequestDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails userDetails) {

//        log.info("tktktkt 2 2");
//
//        System.out.println("replyRequestDto.getContent() = " + replyRequestDto.getContent());
//        System.out.println("boardId = " + boardId);


        /* 핵심기능 */

        Long boardId = replyRequestDto.getBoardId();
        replyService.saveReply(boardId, replyRequestDto, userDetails);



        return new ResponseDto<>(HttpStatus.OK.value(),1);
    }

    // 댓글 삭제
    @DeleteMapping("/api/v1/reply/{id}")
    public ResponseDto<Integer> Delete(@RequestBody ReplyDeleteRequestDto replyDeleteRequestDto) {

        System.out.println("replyDeleteRequestDto.getReplyId() = " + replyDeleteRequestDto.getReplyId());
        
        replyService.deleteReply(replyDeleteRequestDto.getReplyId());


        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }




}
