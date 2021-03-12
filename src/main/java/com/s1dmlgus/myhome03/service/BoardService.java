package com.s1dmlgus.myhome03.service;

import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.board.BoardRepository;
import com.s1dmlgus.myhome03.domain.reply.Reply;
import com.s1dmlgus.myhome03.domain.reply.ReplyRepository;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.web.dto.board.BoardRequestDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardSearchCondition;
import com.s1dmlgus.myhome03.web.dto.reply.ReplyRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Log4j2
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;



    // 전체 리스트 조회
    @Transactional
    public Page<BoardResponseDto> board_list(BoardSearchCondition condition, Pageable pageable) {

        Page<BoardResponseDto> result = boardRepository.searchPage(condition, pageable);

        for (BoardResponseDto boardResponseDto : result) {
            System.out.println("boardResponseDto = " + boardResponseDto);
        }


        return result;

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


    // 게시물 조회
    @Transactional
    public BoardResponseDto findById(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 게시물이 없습니다");
        });
        System.out.println("boardss = " + board);

        return new BoardResponseDto(board);

    }


    // 등록
    @Transactional
    public void saveBoard(BoardRequestDto boardRequestDto, PrincipalDetails userDetails) {

        // 세션
        System.out.println("userDetails = " + userDetails.getUser());

        Member user = userDetails.getUser();
        Board board = boardRequestDto.toEntity(user);


        boardRepository.save(board);


    }


    // 수정
    @Transactional
    public void updateBoard(Long id, BoardRequestDto boardRequestDto) {

        // BoardService findById 함수 활용
        Board board = boardRepository.findById(id).orElseThrow(() -> {

            return new IllegalArgumentException("해당 번호의 게시글은 업습니다");
        });

//        System.out.println("board = " + board);
//        System.out.println("업데이트 비즈니스 로직 전");

        // 비즈니스 로직[업데이트]
        board.update(boardRequestDto.getTitle(), boardRequestDto.getContent());

        
    }

    // 삭제
    public void deleteBoard(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(() -> {

            return new IllegalArgumentException(" 해당 번호의 게시글이 없습니다.");
        });

        // 비즈니스 로직[삭제]
        boardRepository.delete(board);

    }


    // 댓글 등록
    @Transactional
    public void saveReply(Long id, ReplyRequestDto replyRequestDto, PrincipalDetails userDetails){


        //세션 유저
        Member user = userDetails.getUser();
        Board board = boardRepository.findById(id).orElseThrow(() -> {

            return new IllegalArgumentException("해당 번호의 게시물이 없스빈다");
        });

        Reply reply = replyRequestDto.toEntity(board, user);

        Reply save = replyRepository.save(reply);


    }


}
