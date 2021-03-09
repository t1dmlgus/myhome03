package com.s1dmlgus.myhome03.web.controller;


import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.service.BoardService;
import com.s1dmlgus.myhome03.web.dto.board.BoardResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {


    private final BoardService boardService;

    // 게시물 전체 조회
    @GetMapping("/list")
    public String list(Model model, BoardSearchCondition condition, Pageable pageable, @AuthenticationPrincipal PrincipalDetails userDetails){

        try{
            Member user = userDetails.getUser();

            model.addAttribute("user", user);

        }catch (NullPointerException e){

            System.out.println("session_NullPointerException = " + e);
        }

        Page<BoardResponseDto> result = boardService.board_list(condition, pageable);

        
        model.addAttribute("boards", result);

        
        return "board/list";
    }

    
    // 게시물 등록
    @GetMapping("/save")
    public String save(){

        return "board/board_save";
    }


    // 게시물 상세정보
    @GetMapping("/detail")
    public String update(Model model, @RequestParam Long id, @AuthenticationPrincipal PrincipalDetails userDetails){

        
        // 세션 정보 확인

        try{
            Member user = userDetails.getUser();

            model.addAttribute("user", user);
        }catch (NullPointerException e){

            System.out.println("session_NullPointerException = " + e);
        }
        

        BoardResponseDto board = boardService.findById(id);

        String memberName = board.getMemberName();
        System.out.println("memberNamecc = " + memberName);

        String principal = userDetails.getUser().getUsername();
        System.out.println("UserDetails - usernamecc = " + principal);


        model.addAttribute("board", board);


        if(memberName.equals(principal)){
            return "board/board_update";
        }

        return "board/board_detail";
    }


}
