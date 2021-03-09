package com.s1dmlgus.myhome03.web.controller;


import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.service.BoardService;
import com.s1dmlgus.myhome03.web.dto.ResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    @DeleteMapping("/api/v1/board/{id}")
    public ResponseDto<Integer> delete(@PathVariable Long id) {

        boardService.deleteBoard(id);

        return new ResponseDto<>(HttpStatus.OK.value(), 1);

    }





}
