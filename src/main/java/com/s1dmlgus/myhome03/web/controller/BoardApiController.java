package com.s1dmlgus.myhome03.web.controller;


import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.service.BoardService;
import com.s1dmlgus.myhome03.web.dto.ResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;



    // 등록
    @PostMapping("/api/v1/board")
    public ResponseDto<Integer> save(@RequestBody BoardRequestDto boardRequestDto,@AuthenticationPrincipal PrincipalDetails userDetails) {

        System.out.println("boardRequestDto = " + boardRequestDto);
        boardService.saveBoard(boardRequestDto, userDetails);

        return new ResponseDto<>(HttpStatus.OK.value(),1);
    }


    // 조회


}
