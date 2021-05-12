package com.s1dmlgus.myhome03.web.controller;

import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.service.BoardService;
import com.s1dmlgus.myhome03.service.LikesService;
import com.s1dmlgus.myhome03.web.dto.board.BoardResponseDto;
import com.s1dmlgus.myhome03.web.dto.member.MemberResponseDto;
import com.s1dmlgus.myhome03.web.dto.member.SessionMember;
import com.s1dmlgus.myhome03.web.dto.upload.UploadResultDto;
import com.s1dmlgus.myhome03.web.dto.upload.UserLikeBoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Log4j2
@RequiredArgsConstructor
@Controller
public class UserController {


    private final LikesService likesService;
    private final BoardService boardService;



    // 세션정보 확인하기 -> 일반로그인
    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication,
                                          @AuthenticationPrincipal PrincipalDetails userDetails){


        System.out.println("/test/login[세션정보 확인] ----------------------------------------------------------------------------------------");

        System.out.println("Authentication -> PricipalDetails 다운캐스팅");
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal = " + principal.getUser());

        System.out.println("@AuthenticationPrincipal 어노테이션");
        System.out.println("userDetails.getUser() = " + userDetails.getUser());

        return "세션정보 확인하기";

    }

    // 세션정보 확인하기 -> Oauth2 로그인
    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication,
                                               @AuthenticationPrincipal OAuth2User oauth){

        System.out.println("/test/login[세션정보 확인] ----------------------------------------------------------------------------------------");

        System.out.println("Authentication -> OAuth2User 다운캐스팅");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("principal = " + oAuth2User.getAttributes());


        System.out.println("@AuthenticationPrincipal 어노테이션 ");
        System.out.println("oauth = " + oauth.getAttributes());


        return "Oauth 세션정보 확인하기";

    }

    // 로그인 폼
    @GetMapping("/auth/user/loginForm")
    public String login() {

        return "user/loginForm";
    }

    // 회원가입 폼
    @GetMapping("/auth/user/joinForm")
    public String join(){

        return "user/joinForm";
    }



    // 마이페이지
    @GetMapping("user/like")
    public String userLike(Model model, @AuthenticationPrincipal PrincipalDetails userDetails){


        MemberResponseDto memberResponseDto;
        List<UserLikeBoardDto> userLikeBoardDtoList = new ArrayList<>();    // userLikeBoardDto -> 유저가 좋아하는 게시물DTO 생성


        Long userId = userDetails.getSessionMember().getId();

        try{
            SessionMember user = userDetails.getSessionMember();

            model.addAttribute("user", user);

            System.out.println("sessionMember = " + user);
            System.out.println("sessionMember.getUsername = " + user.getUsername());

        }catch (NullPointerException e){
            log.info("NullPointerException = "+e);

        }


        memberResponseDto = likesService.userLikes(userId);
        log.info("memberResponseDto = " + memberResponseDto);


        System.out.println(memberResponseDto.getBoards().size());

        for (Board board : memberResponseDto.getBoards()) {

            log.info("board = " + board.getId());
            
            BoardResponseDto boardResponseDto = boardService.findById(board.getId());            // 게시물 번호에 맞는 이미지 -> dto
            log.info("boardResponseDto = " + boardResponseDto);


            if (boardResponseDto.getUploadResultDtoList().size() != 0){

                UploadResultDto thumbnailImg = boardResponseDto.getUploadResultDtoList().get(0);
                log.info("thumbnailURL : " +thumbnailImg );

                userLikeBoardDtoList.add(new UserLikeBoardDto(boardResponseDto.getBoardId(),thumbnailImg));         

            }
            log.info("userLikeBoardDtoList : " + userLikeBoardDtoList);
            log.info("userLikeBoardDtoLis232323t : " + userLikeBoardDtoList.get(0).getUploadResultDto().getFileName());


        }
        model.addAttribute("userLikeBoardDtoList", userLikeBoardDtoList);


        return "user/myLike";
    }


}
