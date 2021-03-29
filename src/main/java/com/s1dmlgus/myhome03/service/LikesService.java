package com.s1dmlgus.myhome03.service;


import com.querydsl.core.Tuple;
import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.board.BoardRepository;
import com.s1dmlgus.myhome03.domain.boardImage.BoardImage;
import com.s1dmlgus.myhome03.domain.like.LikeRepository;
import com.s1dmlgus.myhome03.domain.like.Likes;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.domain.user.MemberRepository;
import com.s1dmlgus.myhome03.web.dto.boardImage.BoardImageDto;
import com.s1dmlgus.myhome03.web.dto.likes.LikeResponseDto;
import com.s1dmlgus.myhome03.web.dto.likes.LikeSearchCondition;
import com.s1dmlgus.myhome03.web.dto.likes.LikesRequestDto;
import com.s1dmlgus.myhome03.web.dto.member.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikeRepository likeRepository;

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    private int SIZE = 0;
    private LikeSearchCondition likeCond;

    // 좋아요 등록
    @Transactional
    public LikeResponseDto saveLikes(LikesRequestDto likesRequestDto) {


        // boardRepository, memberRepository -> 의존됨

        Member member = memberRepository.findById(likesRequestDto.getUserId()).orElseThrow(() -> {
            return new IllegalArgumentException("해당 번호의 유저는 없습니다.");

        });

        Board board = boardRepository.findById(likesRequestDto.getBoardId()).orElseThrow(() -> {
            return new IllegalArgumentException("해당 번호의 게시물은 없습니02다");
        });


        Likes likes = likesRequestDto.toEntity(member, board);
        System.out.println("likes = " + likes);


        Likes save = likeRepository.save(likes);            // 좋아요 등록

        Long likeCount = likeRepository.countLike(likesRequestDto.getBoardId()); // 좋아요 개수(각 board_id 에 맞는 좋아요 개수 반환)
        System.out.println("likeCount = " + likeCount);



        LikeResponseDto saveLike = new LikeResponseDto(save);
        saveLike.setCount(likeCount);


        System.out.println("saveLike = " + saveLike);


        return saveLike;
    }


    // 게시물 삭제 -> 좋아요 후 게시물 삭제
    @Transactional
    public void deleteBoardLikes(Long boardId) {

        List<Likes> likes = likeRepository.findByBoardId(boardId);

        for (Likes like : likes) {
            System.out.println("like = " + like);

            likeRepository.delete(like);
        }


    }

    // 좋아요 조회
    @Transactional
    public LikeResponseDto findByLike(Long boardId, Long userId) {

        // 검색조건
        likeCond = LikeSearchCondition.builder()
                                            .userId(userId)
                                            .boardId(boardId)
                                            .build();

        System.out.println("좋아요 service~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


        LikeResponseDto likeResponseDto = new LikeResponseDto();

        LikeResponseDto likeDto = likeRepository.boardLike(likeCond);
        Long likeCount = likeRepository.countLike(likeCond.getBoardId());


        if(likeDto != null) {
            likeResponseDto.setLikeId(likeDto.getLikeId());
            likeResponseDto.setStatus(likeDto.getStatus());

        }
        likeResponseDto.setCount(likeCount);


        return likeResponseDto;

//        Long likeCount = likeRepository.countLike(boardId); // 좋아요 개수(각 board_id 에 맞는 좋아요 개수 반환)
//
//        like.setCount(likeCount);




//        List<Likes> likes = likeRepository.findByBoardId(boardId);
//
//        for (Likes like : likes) {
//            like.getMember().getId();
//        }
//
//        List<LikeResponseDto> likeResponseDto = likes.stream().map(LikeResponseDto::new)
//                .collect(Collectors.toList());
//
//
//        for (LikeResponseDto likeResponseDto1 : likeResponseDto) {
//            System.out.println("responseDto = " + likeResponseDto1);
//
//
//            int i = likeResponseDto1.getUserId().compareTo(userId);         // view 화면에 heart 표시
//            System.out.println("i = " + i);
//
//            if (i == 0)
//                likeResponseDto1.setShowHeart(1);
//
//            //likeResponseDto1.setCount(likeResponseDto.size());
//
//        }
//
//        System.out.println("likeResponseDto.si = " + likeResponseDto.size());
//
//
//        for (LikeResponseDto responseDto : likeResponseDto) {
//            System.out.println("responseDto = " + responseDto);
//        }
//
//        return likeResponseDto;
    }

    // 좋아요 삭제
    public Long deleteLikes(Long likeId, Long boardId) {

        likeRepository.deleteById(likeId);
        Long likeCount = likeRepository.countLike(boardId);

        return likeCount;
    }

    // 유저가 좋아요 누른 게시물 조회
    public MemberResponseDto userLikes(Long memberId) {


        List<Likes> userLikes = likeRepository.findByMemberId(memberId);
        List<Board> likeBoard = new ArrayList<>();


        for (Likes userLike : userLikes) {
            Board board = userLike.getBoard();
            likeBoard.add(board);
        }

        System.out.println("likeBoard = " + likeBoard);


        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .boards(likeBoard)
                .build();


        return memberResponseDto;
    }

}
