package com.s1dmlgus.myhome03.web.controller;


import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.service.BoardService;
import com.s1dmlgus.myhome03.service.ReplyService;
import com.s1dmlgus.myhome03.web.dto.ResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardRequestDto;
import com.s1dmlgus.myhome03.web.dto.reply.ReplyRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;
//    private final ReplyService replyService;


    // 등록
    @PostMapping("/api/v1/board")
    public ResponseDto<Integer> save(@Valid @RequestBody BoardRequestDto boardRequestDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails userDetails) {

        log.info("--------------------------gggdg----------------");

        /* 핵심기능 */

        System.out.println("boardRequestDto = " + boardRequestDto);
        boardService.saveBoard(boardRequestDto, userDetails);

        log.info("--------------------------11111g----------------");

        return new ResponseDto<>(HttpStatus.OK.value(),1);
    }


    // 수정
    @PutMapping("/api/v1/board/{id}")   //PathVariable -> {id}
    public ResponseDto<Integer> update(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto){

        System.out.println("id = " + id);
        System.out.println("boardRequestDto = " + boardRequestDto);
        boardService.updateBoard(id,boardRequestDto);

        System.out.println("업데이트 성공");


        return new ResponseDto<>(HttpStatus.OK.value(), 1);


    }

    // 삭제
    @DeleteMapping("/api/v1/board/{boardId}")
    public ResponseDto<Integer> delete(@PathVariable Long boardId) {

        log.info("[boardApiController] boardService.deleteBoard 호출 --------------------------------------");
        boardService.deleteBoard(boardId);

        log.info("[boardApiController] boardService.deleteBoard 끝 --------------------------------------");
        return new ResponseDto<>(HttpStatus.OK.value(), 1);

    }

}
