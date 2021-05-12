package com.s1dmlgus.myhome03.api;

import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.service.BoardService;
import com.s1dmlgus.myhome03.service.MemberService;
import com.s1dmlgus.myhome03.web.dto.ResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardRequestDto;
import com.s1dmlgus.myhome03.web.dto.member.SessionMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RequiredArgsConstructor
@Log4j2
@RestController
public class BoardApiController {


    private final BoardService boardService;
    private final MemberService memberService;


    // 등록
    @PostMapping("/api/v1/board")
    public ResponseDto<Integer> save(@Valid BoardRequestDto boardRequestDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails userDetails) {

        log.info("/api/v1/board --------------------------------------");

        System.out.println("boardRequestDto = " + boardRequestDto);


        // 세션 확인
        try {
            SessionMember sessionMember = userDetails.getSessionMember();
            log.info("sessionMember :" +sessionMember);

//            Member member = memberService.findSessionMember(sessionMember);
//            log.info("member :" +member);

            System.out.println("sessionMember = " + sessionMember);
            System.out.println("sessionMember.getUsername = " + sessionMember.getUsername());

        }catch (NullPointerException e){
            log.info("NullPointerException = "+e);

        }


        /* 핵심기능 */
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
    @DeleteMapping("/api/v1/board/{boardId}")
    public ResponseDto<Integer> delete(@PathVariable Long boardId) {

        log.info("[boardApiController] boardService.deleteBoard 호출 --------------------------------------");
        boardService.deleteBoard(boardId);

        log.info("[boardApiController] boardService.deleteBoard 끝 --------------------------------------");
        return new ResponseDto<>(HttpStatus.OK.value(), 1);

    }
}
