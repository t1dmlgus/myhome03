package com.s1dmlgus.myhome03.web.controller;


import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.like.Likes;
import com.s1dmlgus.myhome03.domain.reply.Reply;
import com.s1dmlgus.myhome03.domain.reply.ReplyRepository;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.domain.user.MemberRepository;
import com.s1dmlgus.myhome03.service.BoardService;
import com.s1dmlgus.myhome03.service.LikesService;
import com.s1dmlgus.myhome03.service.MemberService;
import com.s1dmlgus.myhome03.service.ReplyService;
import com.s1dmlgus.myhome03.web.dto.board.BoardRequestDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardSearchCondition;
import com.s1dmlgus.myhome03.web.dto.likes.LikeResponseDto;
import com.s1dmlgus.myhome03.web.dto.member.MemberResponseDto;
import com.s1dmlgus.myhome03.web.dto.member.SessionMember;
import com.s1dmlgus.myhome03.web.dto.reply.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final ReplyService replyService;
    private final LikesService likesService;


    // 게시물 전체 조회
    @GetMapping("/list")
    public String list(Model model, BoardSearchCondition condition, @PageableDefault(size = 10, sort = "boardId", direction = Sort.Direction.DESC) Pageable pageable,
                       @AuthenticationPrincipal PrincipalDetails userDetails){

        // 세션 확인
        try {
            SessionMember sessionMember = userDetails.getSessionMember();
            Member member = memberService.findSessionMember(sessionMember);

            if (member != null) {
                model.addAttribute("user", member);
            }

            System.out.println("sessionMember = " + sessionMember);
            System.out.println("sessionMember.getUsername = " + sessionMember.getUsername());

        }catch (NullPointerException e){
            log.info("NullPointerException = "+e);

        }



        // 검색조건
        condition = new BoardSearchCondition(condition.getBoardTitle(), condition.getUsername(), condition.getBoardContent());



        Page<BoardResponseDto> result = boardService.board_list(condition, pageable);
        log.info("result.getPageable()" + result.getPageable());
        log.info("result.getContent()" + result.getContent());

        System.out.println(result.getSort());


        // 현재 페이지
        int page = result.getPageable().getPageNumber() + 1;


        System.out.println("page = " + page);

        // 총 페이지 수(= 61)
        int totalPages = result.getTotalPages();

        System.out.println("totalPages = " + totalPages);

                            // 올림
        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;
        System.out.println("tempEnd = " + tempEnd);


        int start = tempEnd - 9;                                    // 페이징리스트 중 첫번째 숫자
        int end = totalPages > tempEnd ? tempEnd : totalPages;      // 페이징리스트 중 마지막 숫자



        boolean prev = start > 1;
        boolean next = totalPages > tempEnd;



        List<Integer> pageList = IntStream.rangeClosed(start, end)
                .boxed().collect(Collectors.toList());
        System.out.println("pageList = " + pageList);


        System.out.println(" =222 " + result.getContent());

        model.addAttribute("boards", result);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageList", pageList);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);



        
        return "board/list";
    }

    
    // 게시물 등록
    @GetMapping("/save")
    public String save(Model model, @AuthenticationPrincipal PrincipalDetails userDetails){

        try{
            //Member user = userDetails.getUser();
            SessionMember user = userDetails.getSessionMember();

            model.addAttribute("user", user);

            System.out.println("sessionMember = " + user);
            System.out.println("sessionMember.getUsername = " + user.getUsername());

        }catch (NullPointerException e){
            log.info("NullPointerException = "+e);

        }

        return "board/board_save";
    }


    // 게시물 상세정보
    @GetMapping("/detail")
    public String detail(Model model, @RequestParam Long id, @AuthenticationPrincipal PrincipalDetails userDetails){

        log.info("detail -------------------------------------------");

        Long boardId = id;       // boardId
        Long sesisonId = null;   // 세션 userId


        // 세션 확인
        try {
            SessionMember sessionMember = userDetails.getSessionMember();
            log.info("sessionMember :" + sessionMember);

            sesisonId = sessionMember.getId();


            model.addAttribute("user", sessionMember);


        } catch (NullPointerException e) {

            log.info("NullPointerException = "+e);
        }


        BoardResponseDto boardDto = boardService.findById(boardId);                 //  boardId
        log.info("boardDto : " + boardDto);


        LikeResponseDto likeDto = likesService.findByLike(boardId, sesisonId);        // boardId, userId
        log.info("likeDto :" +likeDto);


        List<ReplyResponseDto> replyDto = replyService.findByBoard(boardId);        // boardId
        log.info("replyDto :" +replyDto);






        //model.addAttribute("user", user);
        model.addAttribute("board", boardDto);
        model.addAttribute("reply", replyDto);
        model.addAttribute("like", likeDto);



        return "board/board_detail";

    }

}
