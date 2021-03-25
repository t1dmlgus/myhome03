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
import com.s1dmlgus.myhome03.web.dto.board.BoardResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardSearchCondition;
import com.s1dmlgus.myhome03.web.dto.likes.LikeResponseDto;
import com.s1dmlgus.myhome03.web.dto.member.MemberResponseDto;
import com.s1dmlgus.myhome03.web.dto.reply.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String list(Model model, BoardSearchCondition condition, @PageableDefault(size = 10, sort = "boardId", direction = Sort.Direction.DESC) Pageable pageable, @AuthenticationPrincipal PrincipalDetails userDetails){



        try{
            Member user = userDetails.getUser();
            model.addAttribute("user", user);

        }catch (NullPointerException e){
            System.out.println("session_NullPointerException = " + e);
        }


        // 검색조건
        condition = new BoardSearchCondition(condition.getBoardTitle(), condition.getUsername(), condition.getBoardContent());


        Page<BoardResponseDto> result = boardService.board_list(condition, pageable);

        System.out.println(result.getPageable());
        System.out.println(result.getContent());


        System.out.println(result.getSort());

        // 현재 페이지
        int page = result.getPageable().getPageNumber() + 1;
        
        // 총 페이지 수(= 61)
        int totalPages = result.getTotalPages();

        //<td th:each="i : ${#numbers.sequence(1, 61)}">[[${i}]]</td>

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
    @Async
    @GetMapping("/detail")
    public String detail(Model model, @RequestParam Long id, @AuthenticationPrincipal PrincipalDetails userDetails){

        Long userId = null;   // 세션 userId

        // 세션 정보 확인

        try{
            Member user = userDetails.getUser();

            model.addAttribute("user", user);


            userId = user.getId();                                          // 현재 세션 유저(userId)
            //System.out.println(id1);
        }catch (NullPointerException e){

            System.out.println("session_NullPointerException = " + e);
        }


        System.out.println(userId);


        MemberResponseDto member = memberService.findById(userId);

        List<Reply> replies = member.getReplies();

        for (Reply reply : replies) {
            System.out.println("reply = " + reply);
        }


        BoardResponseDto board = boardService.findById(id);                 // 게시판 id
        List<ReplyResponseDto> reply = replyService.findByBoard(id);        // 게시판 id
        LikeResponseDto like = likesService.findByLike(id, userId);        // 게시판 id, 유저 id

        System.out.println("LikeResponseDto = " + like);

        String memberName = board.getMemberName();
        System.out.println("memberNamecc = " + memberName);

        String principal = userDetails.getUser().getUsername();
        System.out.println("UserDetails - usernamecc = " + principal);


        model.addAttribute("board", board);
        model.addAttribute("reply", reply);
        model.addAttribute("like", like);



//        for (LikeResponseDto like : likes) {
//            like.getShowHeart();
//            System.out.println("like.getShowHeart()11111 = " + like.getShowHeart());
//            model.addAttribute("likeShow", like.getShowHeart());
//
//
//        }

//        board.getBoardTitle()
        //System.out.println(reply.get(0).getReplyContent());

        //replys.get(0).getReplyId()

//        if(memberName.equals(principal)){
//            return "board/board_update";
//        }

        return "board/board_detail";
    }


}
