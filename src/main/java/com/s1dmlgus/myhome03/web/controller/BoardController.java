package com.s1dmlgus.myhome03.web.controller;


import com.s1dmlgus.myhome03.service.BoardService;
import com.s1dmlgus.myhome03.web.dto.board.BoardResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {


    private final BoardService boardService;

    // 게시물 전체 조회
    @GetMapping("/list")
    public String list(Model model, BoardSearchCondition condition, Pageable pageable){

        Page<BoardResponseDto> result = boardService.board_list(condition, pageable);

        model.addAttribute("boards", result);

        return "board/list";
    }

    
    // 게시물 등록
    @GetMapping("/save")
    public String save(){

        return "board/board_save";
    }

}
