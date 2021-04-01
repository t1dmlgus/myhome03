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



    // 등록
    @PostMapping("/register")
    public String save(@Valid BoardRequestDto boardRequestDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails userDetails) {

        log.info("--------------------------gggdg----------------");


        System.out.println("boardRequestDto = " + boardRequestDto);

        /* 핵심기능 */

        System.out.println("boardRequestDto = " + boardRequestDto);
        boardService.saveBoard(boardRequestDto, userDetails);

        log.info("--------------------------11111g----------------");

        //return new ResponseDto<>(HttpStatus.OK.value(),1);
        return "redirect:/board/list";
    }


    // 게시물 전체 조회
    @GetMapping("/list")
    public String list(Model model, BoardSearchCondition condition, @PageableDefault(size = 10, sort = "boardId", direction = Sort.Direction.DESC) Pageable pageable, @AuthenticationPrincipal PrincipalDetails userDetails){


        try{
            Member user = userDetails.getUser();
            model.addAttribute("user", user);

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
        
        // 총 페이지 수(= 61)
        int totalPages = result.getTotalPages();



        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;
        int start = tempEnd - 9;
        int end = totalPages > tempEnd ? tempEnd : totalPages;

        boolean prev = start > 1;
        boolean next = totalPages > tempEnd;

        List<Integer> pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        System.out.println("pageList = " + pageList);


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
    public String save(){

        return "board/board_save";
    }


    // 게시물 상세정보
    @GetMapping("/detail")                  // board_id
    public String detail(Model model, @RequestParam Long id, @AuthenticationPrincipal PrincipalDetails userDetails){

        Long userId = null;   // 세션 userId
        Member user = null;

        // 세션 정보 확인
        try{
            user = userDetails.getUser();
            userId = user.getId();                                          // 현재 세션 유저(userId)

            //System.out.println(id1);
        }catch (NullPointerException e){

            log.info("로그인 되어있지 않습니다. "+e);
        }
        System.out.println(userId);

        BoardResponseDto board = boardService.findById(id);                 //  boardId
        List<ReplyResponseDto> reply = replyService.findByBoard(id);        // boardId
        LikeResponseDto like = likesService.findByLike(id, userId);        // boardId, userId


        System.out.println("like@@@@@@ = " + like);

        model.addAttribute("user", user);
        model.addAttribute("board", board);
        model.addAttribute("reply", reply);
        model.addAttribute("like", like);




//        MemberResponseDto member = memberService.findById(userId);
//
//        List<Reply> replies = member.getReplies();
//
//        for (Reply reply : replies) {
//            System.out.println("reply = " + reply);
//        }
//
//



//
//        System.out.println("LikeResponseDto = " + like);
//
//        String memberName = board.getMemberName();
//        System.out.println("memberNamecc = " + memberName);
//
//        String principal = userDetails.getUser().getUsername();
//        System.out.println("UserDetails - usernamecc = " + principal);
//
//





        return "board/board_detail";
    }


}
