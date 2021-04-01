package com.s1dmlgus.myhome03.web.controller;


import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.service.BoardService;
import com.s1dmlgus.myhome03.web.dto.board.BoardNewDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Controller
public class IndexController {

    private final BoardService boardService;

    @GetMapping()
    public String index(Model model, @AuthenticationPrincipal PrincipalDetails userDetails){


        // 세션 확인
        try{
            Member user = userDetails.getUser();

            log.info("user = "+ user);

            model.addAttribute("user", user);

        }catch (NullPointerException e){

            log.info("NullPointerException ="+e);

        }


        // 최신 board 가져오기(9개)
        List<BoardNewDto> boardNewDtos = boardService.board_new();

        model.addAttribute("boardNewDtos", boardNewDtos);



        return "index";
    }


}
