package com.s1dmlgus.myhome03.service;

import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.board.BoardRepository;
import com.s1dmlgus.myhome03.domain.boardImage.BoardImage;
import com.s1dmlgus.myhome03.domain.boardImage.BoardImageRepository;
import com.s1dmlgus.myhome03.domain.reply.Reply;
import com.s1dmlgus.myhome03.domain.reply.ReplyRepository;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.web.dto.board.BoardNewDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardRequestDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardSearchCondition;
import com.s1dmlgus.myhome03.web.dto.boardImage.BoardImageDto;
import com.s1dmlgus.myhome03.web.dto.reply.ReplyRequestDto;
import com.s1dmlgus.myhome03.web.dto.upload.UploadResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Log4j2
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;

    private final ReplyService replyService;
    private final LikesService likesService;


    List<UploadResultDto> uploadResultDtos = new ArrayList<>();

    // 전체 리스트 조회
    @Transactional
    public Page<BoardResponseDto> board_list(BoardSearchCondition condition, Pageable pageable) {

        Page<BoardResponseDto> result = boardRepository.searchPage(condition, pageable);

        for (BoardResponseDto boardResponseDto : result) {
            System.out.println("boardResponseDto = " + boardResponseDto);
        }


        return result;

    }
    // NEW Board 조회
    @Transactional
    public List<BoardNewDto> board_new(){

        List<Board> boards = boardRepository.newBoard();
        List<BoardNewDto> boardNewDtos = new ArrayList<>();

        for (Board board : boards) {

            List<BoardImage> boardImages = boardImageRepository.findByBoardId(board.getId());


            BoardImage boardImage = boardImages.get(0);

            UploadResultDto uploadResultDto = UploadResultDto.builder()
                    .fileName(boardImage.getImgName())
                    .uuid(boardImage.getUuid())
                    .folderPath(boardImage.getPath())
                    .build();

            BoardNewDto boardNewDto = new BoardNewDto(board.getId(), uploadResultDto);
            boardNewDtos.add(boardNewDto);

        }

        System.out.println(boardNewDtos);


        return boardNewDtos;
    }





    // 게시물 조회
    @Transactional
    public BoardResponseDto findById(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 게시물이 없습니다");
        });

        log.info("board : " + board);

        List<BoardImage> boardImages = boardImageRepository.findByBoardId(id);



        for (BoardImage boardImage : boardImages) {

            log.info("boardImage : "+boardImage);

        }

        BoardResponseDto boardResponseDto = new BoardResponseDto(board, boardImages);

        //log.info("boardResponseDto : "+boardResponseDto);


        return boardResponseDto;

    }


    // 등록
    @Transactional
    public void saveBoard(BoardRequestDto boardRequestDto, PrincipalDetails userDetails) {


        System.out.println("userDetails = " + userDetails.getUser());

        // 세션
        Member user = userDetails.getUser();

        // 게시물
        Board board = boardRequestDto.toEntity(user);

        // 게시물 이미지
        List<BoardImageDto> boardImageDtoList = boardRequestDto.getBoardImageDtoList();

        
        // boardImageDtoList null 과 사이즈 0이 아니라는 검증
        if (boardImageDtoList != null && boardImageDtoList.size() > 0){

            
            // boardImageDtoList -> boardImageList 
            List<BoardImage> boardImages = boardImageDtoList.stream().map(boardImageDto -> {

                BoardImage boardImage = BoardImage.builder()
                        .path(boardImageDto.getPath())
                        .imgName(boardImageDto.getImgName())
                        .uuid(boardImageDto.getUuid())
                        .board(board)
                        .build();

                return boardImage;
            }).collect(Collectors.toList());

            
            // board 이미지 저장
            for (BoardImage boardImage : boardImages) {
                System.out.println("boardImage = " + boardImage);
                
                boardImageRepository.save(boardImage);

            }
        }

        // board 저장
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
    @Transactional
    public void deleteBoard(Long boardId) {

        log.info("[boardApiController] boardService.deleteBoard 호출 --------------------------------------");
        // 댓글 선 삭제
        replyService.deleteReplies(boardId);

        // 좋아요 선 삭제
        likesService.deleteBoardLikes(boardId);

        Board board = boardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException(" 해당 번호의 게시글이 없습니다.");
        });

        // 비즈니스 로직[삭제]
        boardRepository.delete(board);

    }



}
