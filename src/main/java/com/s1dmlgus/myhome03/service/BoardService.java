package com.s1dmlgus.myhome03.service;

import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.board.BoardRepository;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.web.dto.board.BoardRequestDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardSearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Log4j2
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;


    // 등록
    @Transactional
    public void saveBoard(BoardRequestDto boardRequestDto, PrincipalDetails userDetails) {

        // 세션
        System.out.println("userDetails = " + userDetails.getUser());

        Member user = userDetails.getUser();
        Board board = boardRequestDto.toEntity(user);


        boardRepository.save(board);


    }

//    // 전체 리스트 조회
//    @Transactional
//    public Page<BoardResponseDto> board_list(Pageable pageable){
//
//        log.info("전체 리스트조회[service]------------------------------------------");
//
//        Page<Board> all = boardRepository.findAll(pageable);
//
//        for (Board board : all) {
//            System.out.println("board = " + board);
//        }
//
//        List<BoardResponseDto> boardListDto
//                = all.stream().map(BoardResponseDto::new)
//                .collect(Collectors.toList());
//
//        return boardListDto;
//    }

    

    // 전체 리스트 조회
    @Transactional
    public Page<BoardResponseDto> board_list(BoardSearchCondition condition, Pageable pageable) {

        Page<BoardResponseDto> result = boardRepository.searchPage(condition, pageable);

        for (BoardResponseDto boardResponseDto : result) {
            System.out.println("boardResponseDto = " + boardResponseDto);
        }


        return result;
        
    }
}
